package org.realityfn.common.registries;

import jakarta.inject.Singleton;
import org.realityfn.common.registries.AuthModels.AuthInfo;
import org.realityfn.common.registries.AuthModels.RouteEntry;

import java.lang.reflect.Method;
import java.util.List;

@Singleton
public class ScopeRegistry {
    private final List<RouteEntry> entries;

    public List<RouteEntry> getEntries() {
        return entries;
    }

    public ScopeRegistry(List<RouteEntry> entries) {
        this.entries = entries;
    }

    public List<AuthInfo> findMatchingAuthInfo(String path, String httpMethod) {
        for (RouteEntry entry : entries) {
            if (entry.pathPattern.matcher(path).matches() && matchesHttpMethod(entry.method, httpMethod)) {
                return entry.authInfo;
            }
        }
        return null;
    }

    private boolean matchesHttpMethod(Method method, String httpMethod) {
        // Check if the method has the corresponding HTTP method annotation
        return method.isAnnotationPresent(jakarta.ws.rs.GET.class) && httpMethod.equals("GET") ||
                method.isAnnotationPresent(jakarta.ws.rs.POST.class) && httpMethod.equals("POST") ||
                method.isAnnotationPresent(jakarta.ws.rs.PUT.class) && httpMethod.equals("PUT") ||
                method.isAnnotationPresent(jakarta.ws.rs.DELETE.class) && httpMethod.equals("DELETE") ||
                method.isAnnotationPresent(jakarta.ws.rs.PATCH.class) && httpMethod.equals("PATCH");
    }

    public boolean requiresAuth(Method method) {
        return entries.stream().anyMatch(entry -> entry.method.equals(method));
    }
}