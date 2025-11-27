package org.realityfn.common.registries.AuthModels;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AuthInfo {
    private final String Resource;
    private final int Action; 

    public AuthInfo(String Resource, int Action) {
        this.Resource = Resource;
        this.Action = Action;
    }

    public String getResource() {
        return Resource;
    }

    public int getAction() {
        return Action;
    }

    /**
     * Deprecated due to PermissionCheckerImpl
     * @param ctx request context
     * @return full resource
     */
    @Deprecated
    public String getRequiredScope(ContainerRequestContext ctx) {
        String resource = Resource;

        // Replace {param} with actual values from path/query
        MultivaluedMap<String, String> pathParams = ctx.getUriInfo().getPathParameters(true);
        MultivaluedMap<String, String> queryParams = ctx.getUriInfo().getQueryParameters(true);

        for (Map.Entry<String, List<String>> entry : pathParams.entrySet()) {
            resource = resource.replaceAll(Pattern.quote("{" + entry.getKey() + "}"), entry.getValue().get(0));
        }
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            resource = resource.replaceAll(Pattern.quote("{" + entry.getKey() + "}"), entry.getValue().get(0));
        }
        return resource;
    }
}