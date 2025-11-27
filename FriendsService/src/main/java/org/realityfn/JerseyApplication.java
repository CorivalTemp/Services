package org.realityfn;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.Path;
import org.realityfn.annotations.OAuth;
import org.realityfn.filters.OAuthFilter;
import org.realityfn.registries.AuthModels.AuthInfo;
import org.realityfn.registries.AuthModels.RouteEntry;
import org.realityfn.registries.ScopeRegistry;
import org.realityfn.utils.XMPPEventService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationPath("/friends")
public class JerseyApplication extends ResourceConfig {
    public JerseyApplication(XMPPEventService xmppService) {
        packages("org.realityfn");

        List<RouteEntry> entries = new ArrayList<>();
        for (Class<?> clazz : getClasses()) {
            Path classPathAnn = clazz.getAnnotation(Path.class);
            String classPath = classPathAnn != null ? classPathAnn.value() : "";

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(OAuth.class)) {
                    OAuth scoped = method.getAnnotation(OAuth.class);

                    Path methodPathAnn = method.getAnnotation(Path.class);
                    String methodPath = methodPathAnn != null ? methodPathAnn.value() : "";

                    String fullPath = (classPath + "/" + methodPath).replaceAll("//+", "/");
                    String regexPath = fullPath.replaceAll("\\{[^/]+\\}", "[^/]+");
                    Pattern pathPattern = Pattern.compile("^" + regexPath + "$");
                    entries.add(new RouteEntry(
                            pathPattern,
                            method,
                            new AuthInfo(scoped.resource(), scoped.action())
                    ));
                }
            }
        }

        ScopeRegistry scopeRegistry = new ScopeRegistry(entries);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(xmppService).to(XMPPEventService.class);
                bind(scopeRegistry).to(ScopeRegistry.class);
            }
        });
    }
}