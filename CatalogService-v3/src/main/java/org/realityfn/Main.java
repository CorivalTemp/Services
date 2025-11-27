package org.realityfn;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.realityfn.common.utils.AdminAccess;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static List<HttpServer> startServers() throws IOException {
        List<HttpServer> servers = new java.util.ArrayList<>(List.of());

        final ResourceConfig rc = new CatalogPublicApplication();
        final HttpServer serverPublic = GrizzlyHttpServerFactory.createHttpServer(

                URI.create(AdminAccess.BASE_PUBLIC_URI), rc, false);

        final ResourceConfig rcAdmin = new CatalogAdminApplication();
        final HttpServer serverAdmin = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(AdminAccess.BASE_ADMIN_URI), rcAdmin, false);

        servers.add(serverPublic);
        servers.add(serverAdmin);
        for (HttpServer server : servers) {
            ServerConfiguration config = server.getServerConfiguration();
            config.addHttpHandler(new HttpHandler() {
                @Override
                public void service(Request request, Response response) {
                    response.setStatus(404);
                    response.setContentLength(0);
                    response.finish();
                }
            }, "/");

            server.start();
        }
        return servers;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Logger logger = Logger.getLogger("ServerInitiater");
        final List<HttpServer> serversList = startServers();
        logger.log(Level.INFO, MessageFormat.format("All servers running on {0} and {1}",
                AdminAccess.BASE_PUBLIC_URI, AdminAccess.BASE_ADMIN_URI));

        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (HttpServer server : serversList) {
                server.shutdown();
            }
            latch.countDown();
        }));

        latch.await();
    }
}