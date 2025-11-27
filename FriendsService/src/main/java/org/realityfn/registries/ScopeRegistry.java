package org.realityfn.registries;

import jakarta.inject.Singleton;
import org.realityfn.registries.AuthModels.AuthInfo;
import org.realityfn.registries.AuthModels.RouteEntry;

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

    public AuthInfo findMatchingAuthInfo(String requestPath) {
        for (RouteEntry entry : entries) {
            if (entry.pathPattern.matcher(requestPath).matches()) {
                return entry.authInfo;
            }
        }
        return null;
    }
}