package org.realityfn.fortnite.core.filters;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;
import org.realityfn.common.models.OAuthToken;
import org.realityfn.common.registries.AuthModels.AuthInfo;
import org.realityfn.common.registries.ScopeRegistry;
import org.realityfn.fortnite.core.exceptions.fortnite.MissingActionException;
import org.realityfn.fortnite.core.models.mongo.LightswitchDataEntry;
import org.realityfn.fortnite.core.utils.repositories.lightswitch.LightswitchRepository;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class LightswitchFilter implements ContainerRequestFilter {
    @Inject
    private ScopeRegistry scopeRegistry;

    @Inject
    private ResourceInfo resourceInfo;

    private final LightswitchRepository lightswitchRepository;

    @Inject
    public LightswitchFilter(LightswitchRepository lightswitchRepository) {
        this.lightswitchRepository = lightswitchRepository;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws MissingActionException {

        Method method = resourceInfo.getResourceMethod();
        if (!scopeRegistry.requiresAuth(method)) return;

        String requestPath = "/" + containerRequestContext.getUriInfo().getPath().replaceAll("^/+", "");
        String httpMethod = containerRequestContext.getMethod();

        List<AuthInfo> authInfo = scopeRegistry.findMatchingAuthInfo(requestPath, httpMethod);
        if (authInfo == null) {
            return;
        }

        OAuthToken credentials = (OAuthToken) containerRequestContext.getProperty("authToken");

        if (!Objects.equals(credentials.auth_method, "client_credentials")) {
            Optional<LightswitchDataEntry> accountStatusOpt = lightswitchRepository.findById(credentials.account_id);

            if (accountStatusOpt.isEmpty()) {
                throw new MissingActionException();
            }

            LightswitchDataEntry accountStatus = accountStatusOpt.get();
            boolean isFortniteAllowed = accountStatus.getServices().stream()
                    .filter(service -> "Fortnite".equals(service.getServiceInstanceId()))
                    .findFirst()
                    .map(service -> !service.isBanned() && service.getAllowedActions().contains("PLAY"))
                    .orElse(true);

            if (!isFortniteAllowed) {
                throw new MissingActionException();
            }
        }
    }
}
