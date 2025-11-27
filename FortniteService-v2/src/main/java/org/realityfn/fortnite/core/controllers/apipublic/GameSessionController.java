package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.common.annotations.Valid;
import org.realityfn.common.models.OAuthToken;
import org.realityfn.fortnite.core.exceptions.modules.matchmaking.PlayerNotRegisteredException;
import org.realityfn.fortnite.core.exceptions.modules.matchmaking.UnknownSessionException;
import org.realityfn.fortnite.core.models.gamesessions.GameSessionDataEntry;
import org.realityfn.fortnite.core.utils.repositories.fortnite.GameSessionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Path("/api/matchmaking")
@Produces(MediaType.APPLICATION_JSON)
public class GameSessionController {

    @Context
    private ContainerRequestContext requestContext;

    private final GameSessionRepository gameSessionRepository;

    @Inject
    public GameSessionController(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @POST
    @Path("/session")
    @Consumes(MediaType.APPLICATION_JSON)
    @OAuth(resource = "matchmaking:session", action = Actions.CREATE)
    public Response createGameSession(@Valid(true) GameSessionDataEntry sessionDataEntry) {
        if (sessionDataEntry.getId() == null) {
            sessionDataEntry.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
            sessionDataEntry.setOwnerId(java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase());
        }
        sessionDataEntry.setTotalPlayers(sessionDataEntry.getPrivatePlayers().size());
        sessionDataEntry.setLastUpdated(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        gameSessionRepository.createOrUpdateSession(sessionDataEntry);
        return Response.ok(sessionDataEntry).build();
    }

    @POST
    @Path("/session/{sessionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @OAuth(resource = "matchmaking:session:{sessionId}", action = Actions.CREATE)
    public Response createOrUpdateGameSession(@PathParam("sessionId") String sessionId, @Valid(true) GameSessionDataEntry sessionDataEntry) {
        sessionDataEntry.setId(sessionId);

        sessionDataEntry.setTotalPlayers(sessionDataEntry.getPrivatePlayers().size());
        sessionDataEntry.setLastUpdated(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        gameSessionRepository.createOrUpdateSession(sessionDataEntry);
        return Response.ok(sessionDataEntry).build();
    }

    @PUT
    @Path("/session/{sessionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @OAuth(resource = "matchmaking:session:{sessionId}", action = Actions.CREATE)
    public Response updateGameSession(@PathParam("sessionId") String sessionId, @Valid(true) GameSessionDataEntry sessionDataEntry) {
        sessionDataEntry.setId(sessionId);

        sessionDataEntry.setTotalPlayers(sessionDataEntry.getPrivatePlayers().size());
        sessionDataEntry.setLastUpdated(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        gameSessionRepository.createOrUpdateSession(sessionDataEntry);
        return Response.ok(sessionDataEntry).build();
    }

    @GET
    @Path("/session/{sessionId}")
    @OAuth(resource = "matchmaking:session:{sessionId}", action = Actions.READ)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchGameSession(@PathParam("sessionId") String sessionId, @QueryParam("sessionKey") String sessionKey) {
        Optional<GameSessionDataEntry> sessionInfoOpt = gameSessionRepository.findSessionById(sessionId);
        if (sessionInfoOpt.isEmpty()) throw new UnknownSessionException(sessionId);

        OAuthToken oAuthToken = (OAuthToken) requestContext.getProperty("authToken");
        String accountId = oAuthToken.account_id;

        GameSessionDataEntry sessionInfo = sessionInfoOpt.get();
        sessionInfo.clearPlayerArrays();

        /*
         Check if the requesting account is registered to the session or has rights to fetch session info JIRA: RLTY-real
        if (!oAuthToken.isClientCredentials() || !sessionInfo.fetchSessionKey().equals(sessionKey) || !sessionInfo.getPrivatePlayers().contains(accountId)) {
            throw new PlayerNotRegisteredException(accountId);
        }
        */
        return Response.ok(sessionInfo).build();
    }

    @POST
    @Path("/session/{sessionId}/join")
    @OAuth(resource = "matchmaking:session:{sessionId}", action = Actions.READ)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinGameSession(@PathParam("sessionId") String sessionId, @QueryParam("sessionKey") String sessionKey, @QueryParam("accountId") String accountId) {
        Optional<GameSessionDataEntry> sessionInfoOpt = gameSessionRepository.findSessionById(sessionId);
        if (sessionInfoOpt.isEmpty()) throw new UnknownSessionException(sessionId);

        GameSessionDataEntry sessionInfo = sessionInfoOpt.get();
        sessionInfo.getPublicPlayers().add(accountId);
        gameSessionRepository.update(sessionInfo);
        return Response.noContent().build();
    }

    @GET
    @Path("/session/findPlayer/{accountId}")
    @OAuth(resource = "matchmaking:session", action = Actions.READ)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPlayer(@PathParam("accountId") String accountId) {
        List<GameSessionDataEntry> sessionList = gameSessionRepository.findAllSessionsByAccountId(accountId);

        OAuthToken oAuthToken = (OAuthToken) requestContext.getProperty("authToken");
        String owningAccountId = oAuthToken.account_id;


        // More filtering for BR lists and stuff should be done, although isn't a priority at the moment
        for (GameSessionDataEntry sessionInfo : sessionList) {
            if (sessionInfo.getAttributes().getOrDefault("GAMEMODE_s", "FORTEMPTY").equals("FORTATHENA")) {
                sessionInfo.clearPlayerArrays();
            }
        }
        return Response.ok(sessionList).build();
    }
}