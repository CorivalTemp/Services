package org.realityfn;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.realityfn.utils.XMPPEventService;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:7883/";

    public static HttpServer startServer(XMPPEventService xmppService) {
        final JerseyApplication rc = new JerseyApplication(xmppService);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //Logger.getLogger("").setLevel(Level.ALL);
        //for (Handler handler : Logger.getLogger("").getHandlers()) {
        //    handler.setLevel(Level.ALL);
        //}

        System.setProperty("rocks.xmpp.debug", "true");
        final XMPPEventService xmppService = new XMPPEventService();
        xmppService.connect();
        final HttpServer server = startServer(xmppService);
        System.out.println(String.format("Jersey app started with endpoints available at %s", BASE_URI));

        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdownNow();
            latch.countDown();
            xmppService.disconnect();
        }));

        latch.await(); // HACK: there's no other way to keep it up on docker
    }
}