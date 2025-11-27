package org.realityfn.common;

import jakarta.inject.Singleton;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.Path;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.registries.AuthModels.AuthInfo;
import org.realityfn.common.registries.AuthModels.RouteEntry;
import org.realityfn.common.registries.ScopeRegistry;
import org.realityfn.common.services.ValidationInterceptionService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class BaseApplication extends ResourceConfig {

    public BaseApplication() {
        registerResources();
        setupOAuthRegistration();
    }
    protected abstract void registerResources();

    private void setupOAuthRegistration() {
        property("jersey.config.server.wadl.disableWadl", true);
        packages("org.realityfn.common");
        List<RouteEntry> entries = new ArrayList<>();

        for(Class<?> clazz : this.getClasses()) {
            Path classPathAnn = clazz.getAnnotation(Path.class);
            String classPath = classPathAnn != null ? classPathAnn.value() : "";

            for(Method method : clazz.getDeclaredMethods()) {
                OAuth[] oauthAnnotations = method.getAnnotationsByType(OAuth.class);

                if (oauthAnnotations.length > 0) {
                    Path methodPathAnn = method.getAnnotation(Path.class);
                    String methodPath = methodPathAnn != null ? methodPathAnn.value() : "";
                    String fullPath = (classPath + "/" + methodPath).replaceAll("//+", "/");
                    String regexPath = fullPath.replaceAll("\\{[^/]+\\}", "[^/]+");
                    Pattern pathPattern = Pattern.compile("^" + regexPath + "$");

                    // Create a RouteEntry with ALL OAuth requirements
                    List<AuthInfo> authInfos = new ArrayList<>();
                    for(OAuth scoped : oauthAnnotations) {
                        authInfos.add(new AuthInfo(scoped.resource(), scoped.action().getValue()));
                    }

                    entries.add(new RouteEntry(pathPattern, method, authInfos));
                }
            }
        }

        final ScopeRegistry scopeRegistry = new ScopeRegistry(entries);
        register(new AbstractBinder() {
            protected void configure() {
                bind(scopeRegistry).to(ScopeRegistry.class);
                bind(ValidationInterceptionService.class)
                        .to(InterceptionService.class)
                        .in(Singleton.class);
            }
        });
    }
}