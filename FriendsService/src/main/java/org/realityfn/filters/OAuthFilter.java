package org.realityfn.filters;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import org.realityfn.errorhandling.exceptions.common.AuthenticationFailedException;
import org.realityfn.errorhandling.exceptions.oauth.InvalidOAuthTokenException;
import org.realityfn.models.OAuthToken;
import org.realityfn.registries.AuthModels.AuthInfo;
import org.realityfn.registries.ScopeRegistry;
import org.realityfn.utils.OAuthService;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class OAuthFilter implements ContainerRequestFilter {
    @Inject
    private ScopeRegistry scopeRegistry;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException, AuthenticationFailedException, InvalidOAuthTokenException {
        String authorization = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.toLowerCase().startsWith("bearer ")) {
            throw new AuthenticationFailedException(containerRequestContext.getUriInfo().getPath());
        }

        String token = authorization.split(" ")[1];

        String requestPath = "/" + containerRequestContext.getUriInfo().getPath().replaceAll("^/+", "");

        AuthInfo authInfo = scopeRegistry.findMatchingAuthInfo(requestPath);
        if (authInfo == null) {
            return; // This means there is no @OAuth annotation on the endpoint
        }

        OAuthToken authToken = null;
        authToken = OAuthService.validateToken(token, authInfo.getRequiredScope(containerRequestContext), authInfo.getAction());
        if (authToken == null) {
            throw new InvalidOAuthTokenException();
        }

        containerRequestContext.setProperty("authToken", authToken);
    }
}
