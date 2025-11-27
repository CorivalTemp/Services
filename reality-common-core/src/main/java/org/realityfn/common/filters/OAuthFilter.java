package org.realityfn.common.filters;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import org.realityfn.common.enums.Actions;
import org.realityfn.common.exceptions.common.authentication.AuthenticationFailedException;
import org.realityfn.common.exceptions.common.oauth.InvalidOAuthTokenException;
import org.realityfn.common.models.OAuthToken;
import org.realityfn.common.registries.AuthModels.AuthInfo;
import org.realityfn.common.registries.ScopeRegistry;
import org.realityfn.common.utils.OAuthService;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class OAuthFilter implements ContainerRequestFilter {
    @Inject
    private ScopeRegistry scopeRegistry;

    @Inject
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws AuthenticationFailedException, InvalidOAuthTokenException {
        Method method = resourceInfo.getResourceMethod();
        if (!scopeRegistry.requiresAuth(method)) return;

        String authorization = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.toLowerCase().startsWith("bearer ")) {
            throw new AuthenticationFailedException("/" + containerRequestContext.getUriInfo().getPath());
        }

        String token = authorization.split(" ")[1];

        String requestPath = "/" + containerRequestContext.getUriInfo().getPath().replaceAll("^/+", "");
        String httpMethod = containerRequestContext.getMethod();

        List<AuthInfo> authInfos = scopeRegistry.findMatchingAuthInfo(requestPath, httpMethod);
        if (authInfos == null) {
            return; // This means there is no @OAuth annotation on the endpoint
        }

        List<String> resources = authInfos.stream()
                .map(AuthInfo::getResource)
                .toList();

        List<Integer> action = authInfos.stream().map(AuthInfo::getAction).toList();

        OAuthToken authToken = OAuthService.validateToken(token, resources, action, containerRequestContext.getUriInfo());
        if (authToken == null) {
            throw new InvalidOAuthTokenException();
        }

        containerRequestContext.setProperty("authToken", authToken);
    }
}
