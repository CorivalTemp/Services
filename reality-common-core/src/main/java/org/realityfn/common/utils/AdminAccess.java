package org.realityfn.common.utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.UriInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdminAccess {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = AdminAccess.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }
    }

    public static final String ADMIN_HOST = props.getProperty("admin.host");

    public static final String PUBLIC_APP_NAME = props.getProperty("public.app.name");
    public static final String ADMIN_APP_NAME = props.getProperty("admin.app.name");

    public static final int PUBLIC_PORT = Integer.parseInt(props.getProperty("public.port"));
    public static final int ADMIN_PORT = Integer.parseInt(props.getProperty("admin.port"));

    public static final String BASE_PUBLIC_URI = "http://0.0.0.0:" + PUBLIC_PORT + "/";
    public static final String BASE_ADMIN_URI = "http://0.0.0.0:" + ADMIN_PORT + "/";

    private static String fetchCurrentAppName(String host) {
        if (host != null && (host.startsWith(ADMIN_HOST) || isLocalHost(host))) {
            return ADMIN_APP_NAME;
        }
        return PUBLIC_APP_NAME;
    }

    private static String fetchCurrentAppName(String host, int port) {
        if (port == ADMIN_PORT) {
            return ADMIN_APP_NAME;
        }

        if (port == PUBLIC_PORT) {
            return PUBLIC_APP_NAME;
        }

        return fetchCurrentAppName(host);
    }

    public static String fetchCurrentAppName(ContainerRequestContext requestContext) {
        String host = requestContext.getHeaderString("Host");
        UriInfo uriInfo = requestContext.getUriInfo();
        int port = uriInfo.getBaseUri().getPort();

        return fetchCurrentAppName(host, port);
    }

    private static boolean isLocalHost(String host) {
        return host != null && (host.startsWith("localhost") || host.startsWith("127.0.0.1"));
    }
}