package org.realityfn.fortnite.core.models.fortnite.matchmakingservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.common.utils.AdminAccess;
import org.realityfn.fortnite.core.managers.HmacManager;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchmakingTicketResponse {

    @JsonIgnore
    private static final Properties props = new Properties();

    static {
        try (InputStream is = MatchmakingTicketResponse.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }
    }

    private String serviceUrl;
    private String ticketType;
    private String payload;
    private String signature;

    public MatchmakingTicketResponse() {
    }

    public MatchmakingTicketResponse(String serviceUrl, String ticketType, String payload) {
        this.serviceUrl = serviceUrl;
        this.ticketType = ticketType;
        this.payload = payload;
        this.signature = HmacManager.generate(payload, props.getProperty("config.org.realityfn.fortnite.core.matchmaking.secret_key"));
    }

    public MatchmakingTicketResponse(String serviceUrl, String ticketType, String payload, String signature) {
        this.serviceUrl = serviceUrl;
        this.ticketType = ticketType;
        this.payload = payload;
        this.signature = signature;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
