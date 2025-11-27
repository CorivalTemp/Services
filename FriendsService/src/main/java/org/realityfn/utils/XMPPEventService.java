package org.realityfn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.net.ChannelEncryption;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.session.XmppSessionConfiguration;
import rocks.xmpp.core.session.debug.ConsoleDebugger;
import rocks.xmpp.core.stanza.model.*;
import rocks.xmpp.im.chat.ChatManager;
import rocks.xmpp.im.chat.ChatSession;
import rocks.xmpp.im.roster.RosterManager;
import rocks.xmpp.websocket.net.client.WebSocketConnectionConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMPPEventService {

    private static final Logger LOGGER = Logger.getLogger(XMPPEventService.class.getName());

    private static final boolean isXMPPEnabled = true;

    private static final String XMPP_WEBSOCKET_HOST = "xmpp-service-prod.realityfn.org";
    private static final String XMPP_USERNAME = "xmpp-admin";
    private static final String XMPP_DOMAIN = "prod.realityfn.org";

    private XmppClient connection;
    private ChatManager chatManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public XMPPEventService() {
        LOGGER.info("XMPPEventService instance created.");
    }

    public boolean connect() {
        if (!isXMPPEnabled) {
            LOGGER.log(Level.WARNING, "XMPP is disabled for this instance.");
            return false;
        }
        try {
            HttpResponse<JsonNode> response = Unirest.post("https://account-public-service-prod03.realityfn.org/account/api/oauth/token")
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(("ba8ad541c2c54ce7bf4d7151e3e9f21d:f1f884e20d024d69b1669dc77b4aa95e").getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("grant_type", "client_credentials")
                    .asJson();

            String xmppPassword = response.getBody().getObject().getString("access_token");

            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());


            final WebSocketConnectionConfiguration wsConfig =
                    WebSocketConnectionConfiguration.builder()
                            .hostname(XMPP_WEBSOCKET_HOST)
                            .port(443)
                            .path("")
                            .sslContext(sc)
                            .channelEncryption(ChannelEncryption.DIRECT)
                            .build();


            final XmppSessionConfiguration sessionConfig = XmppSessionConfiguration.builder()
                    .authenticationMechanisms("PLAIN")
                    .closeOnShutdown(false)
                    .extensions()
                    .initialPresence(() -> {
                        Presence p = new Presence(Presence.Show.CHAT, (byte)5);
                        p.setStatus("Online");
                        return p;
                    })
                    .debugger(ConsoleDebugger.class)
                    .build();

            this.connection = XmppClient.create(XMPP_DOMAIN, sessionConfig, wsConfig);

            RosterManager roster = connection.getManager(RosterManager.class);
            roster.setRetrieveRosterOnLogin(true);

            connection.enableFeature("urn:xmpp:ping");

            connection.addSessionStatusListener(e -> {
                if (e.getStatus() == XmppSession.Status.AUTHENTICATED) {
                    chatManager = connection.getManager(ChatManager.class);
                }
            });

            connection.connect();
            connection.login(XMPP_USERNAME, xmppPassword);

            this.chatManager = connection.getManager(ChatManager.class);
            LOGGER.info("XMPP Event Service connected and authenticated successfully.");
            return true;

        } catch (XmppException | NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect and login to XMPP Event Service.", e);
            return false;
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }


    public void disconnect() {
        if (!isXMPPEnabled) {
            LOGGER.log(Level.WARNING, "XMPP is disabled for this instance.");
            return;
        }
        LOGGER.info("Disconnecting XMPP Connection.");
        if (connection != null && connection.isConnected()) {
            try {
                connection.close();
                LOGGER.info("XMPP Event Service disconnected.");
            } catch (XmppException e) {
                LOGGER.log(Level.SEVERE, "Error while disconnecting from XMPP service.", e);
            }
        }
    }

    public boolean sendEvent(String recipient, String eventType, Map<String, Object> payload) {
        if (!isXMPPEnabled) {
            LOGGER.log(Level.WARNING, "XMPP is disabled for this instance.");
            return false;
        }

        if (!isConnected()) {
            LOGGER.warning("XMPP connection not available for event.");
            return false;
        }

        try {
            Jid jid = Jid.of(recipient);
            String eventMessage = createEventMessage(eventType, payload);
            Message rawMessage = new Message(jid, Message.Type.NORMAL);
            rawMessage.setBody(eventMessage);
            connection.send(rawMessage);

            LOGGER.info("Event sent to " + recipient + ": " + eventType);
            return true;
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Failed to send event to " + recipient, e);
            return false;
        }
    }

    public boolean sendLegacyEvent(String recipient, Map<String, Object> payload) {
        if (!isXMPPEnabled) {
            LOGGER.log(Level.WARNING, "XMPP is disabled for this instance.");
            return false;
        }

        if (!isConnected()) {
            LOGGER.warning("XMPP connection not available for event.");
            return false;
        }

        try {
            Jid jid = Jid.of(recipient);
            ObjectNode payloadNode = objectMapper.convertValue(payload, ObjectNode.class);
            Message rawMessage = new Message(jid, Message.Type.NORMAL);
            rawMessage.setBody(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payloadNode));
            LOGGER.info("Event sent to " + recipient + ": " + payload.get("type"));
            return true;
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Failed to send event to " + recipient, e);
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createEventMessage(String eventType, Map<String, Object> payload) {
        System.out.println(payload);
        try {
            ObjectNode root = objectMapper.createObjectNode();
            root.put("type", eventType);
            root.put("timestamp", Instant.now().toString());
            ObjectNode payloadNode = objectMapper.convertValue(payload, ObjectNode.class);
            root.set("payload", payloadNode);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create event message JSON", e);
        }
    }

    public boolean isConnected() {
        if (!isXMPPEnabled) {
            LOGGER.log(Level.WARNING, "XMPP is disabled for this instance.");
            return false;
        }
        return connection != null && connection.isConnected() && connection.isAuthenticated();
    }
}