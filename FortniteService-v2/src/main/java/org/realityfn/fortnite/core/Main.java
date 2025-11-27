package org.realityfn.fortnite.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.http.UriCompliance;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.server.ResourceConfig;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.templates.FortniteTemplates;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.realityfn.common.utils.AdminAccess;
import org.realityfn.fortnite.core.config.FortniteConfig;
import org.realityfn.fortnite.core.config.LightswitchConfig;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static ApplicationContext springContext;

    private static final String appName = "fortnite";

    public static List<Server> startServers() throws Exception {

        // If anything needs initialization add it here not FortniteApplication to avoid double initiating
        //TemplateManager.initialize("/app/resources");
        TemplateManager.initialize("");
        FortniteTemplates.initialize();

        List<Server> servers = new java.util.ArrayList<>();

        springContext = new AnnotationConfigApplicationContext(FortniteConfig.class, LightswitchConfig.class);

        final ResourceConfig rcPublic = new FortnitePublicApplication();
        URI publicUri = URI.create(AdminAccess.BASE_PUBLIC_URI);
        Server serverPublic = createJettyServer(publicUri, rcPublic);

        rcPublic.register(new AbstractContainerLifecycleListener() {
            @Override
            public void onStartup(Container container) {
                ServiceLocator locator = container.getApplicationHandler()
                        .getInjectionManager()
                        .getInstance(ServiceLocator.class);

                if (locator == null) {
                    Logger.getLogger("Migration").log(Level.SEVERE, "Failed to retrieve ServiceLocator from ApplicationHandler configuration.");
                    return;
                }

                ProfileManager profileManager = locator.getService(ProfileManager.class);

                try {
                    if (profileManager != null) {
                        profileManager.migrateAllProfiles();
                    } else {
                        Logger.getLogger("Migration").log(Level.SEVERE, "Could not find ProfileManager to run migration!");
                    }
                } catch (Exception e) {
                    Logger.getLogger("Migration").log(Level.SEVERE, "Migration failed on startup", e);
                }
            }
        });

        final ResourceConfig rcAdmin = new FortniteAdminApplication();
        URI adminUri = URI.create(AdminAccess.BASE_ADMIN_URI);
        Server serverAdmin = createJettyServer(adminUri, rcAdmin);

        servers.add(serverPublic);
        servers.add(serverAdmin);

        // Start all servers
        for (Server server : servers) {
            server.start();
        }

        return servers;
    }

    private static Server createJettyServer(URI uri, ResourceConfig resourceConfig) {
        Server server = new Server();

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setUriCompliance(UriCompliance.LEGACY);

        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        connector.setHost(uri.getHost());
        connector.setPort(uri.getPort());

        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
        context.addServlet(servletHolder, String.format("/%s/*", appName));

        HandlerList handlers = getHandlerList(context);
        server.setHandler(handlers);

        return server;
    }

    private static HandlerList getHandlerList(ServletContextHandler context) {
        AbstractHandler notFoundHandler = new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                               HttpServletRequest request, HttpServletResponse response) {
                if (!target.startsWith("/" + appName + "/") && !target.equals("/" + appName)) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.setContentLength(0);

                    try { while (request.getInputStream().read() != -1) {} } catch (Exception ignored) {}

                    baseRequest.setHandled(true);
                }
                response.setHeader("Server", "fn-gateway");
                response.setHeader("X-Epicgames-Mcpversion", BuildProperties.MCP_VERSION);
            }
        };

        // Combine handlers
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{notFoundHandler, context});
        return handlers;
    }

    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger("ServerInitiater");
        final List<Server> serversList = startServers();
        logger.log(Level.INFO, MessageFormat.format("All servers running on {0} and {1}",
                AdminAccess.BASE_PUBLIC_URI, AdminAccess.BASE_ADMIN_URI));

        CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Server server : serversList) {
                try {
                    server.stop();
                    server.destroy();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error stopping server", e);
                }
            }

            if (springContext instanceof AnnotationConfigApplicationContext) {
                ((AnnotationConfigApplicationContext) springContext).close();
            }
            latch.countDown();
        }));

        latch.await();
    }
}