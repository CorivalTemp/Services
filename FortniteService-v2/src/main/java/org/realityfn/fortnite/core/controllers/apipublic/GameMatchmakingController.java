package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.models.gamesessions.SessionEncryptionKeyResponse;

@Path("/api/game/v2/matchmaking")
@Produces(MediaType.APPLICATION_JSON)
public class GameMatchmakingController {

    @GET
    @Path("/account/{accountId}/session/{sessionId}")
    @OAuth(resource = "profile:{accountId}:commands", action = Actions.ALL)
    public Response querySessionEncryptionKey(@PathParam(value = "accountId") String accountId, @PathParam(value = "sessionId") String sessionId) {
        SessionEncryptionKeyResponse response = new SessionEncryptionKeyResponse(accountId, sessionId, "AOJEv8uTFmUh7XM2328kq9rlAzeQ5xzWzPIiyKn2s7s");
        return Response.ok(response).build(); // very obvious placeholders here
    }
}