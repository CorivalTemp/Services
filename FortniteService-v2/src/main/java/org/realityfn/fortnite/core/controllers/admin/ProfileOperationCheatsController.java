package org.realityfn.fortnite.core.controllers.admin;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.models.profiles.ProfileResponse;
import org.realityfn.fortnite.core.models.profiles.ProfileRevision;
import org.realityfn.fortnite.core.services.FortniteService;

import java.util.List;


@Path("/api/game/v2/profile/{accountId}/developer_cheats")
public class ProfileOperationCheatsController {

    final ProfileManager profileManager;

    @Inject
    public ProfileOperationCheatsController(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Context
    private UriInfo uriInfo;

    @Context
    private ContainerRequestContext requestContext;

    @POST
    @Path("/{command}")
    @OAuth(resource = "profile:{accountId}:commands", action = Actions.ALL)
    @OAuth(resource = "fortnite_role:developer_cheats", action = Actions.ALL)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeProfileCommand(
            @PathParam("accountId") String accountId,
            @PathParam("command") String command,
            @QueryParam("profileId") @DefaultValue("common_core") String profileId,
            @QueryParam("rvn") @DefaultValue("-1") int rvn,
            @HeaderParam("X-EpicGames-ProfileRevisions") String profileRevisionsJson,
            byte[] body)
    {
        List<ProfileRevision> profileRevisions = FortniteService.parseProfileRevisions(profileRevisionsJson);

        // Only use the query param if the header doesn't exist or is missing the profile we require
        if (!profileRevisions.isEmpty()) {
            for (ProfileRevision profileRevision : profileRevisions) {
                if (profileRevision.getProfileId().equals(profileId)) {
                    rvn = profileRevision.getClientCommandRevision();
                    break;
                }
            }
        }

        ProfileResponse response = profileManager.runProfileCommand(
                ProfileRoute.CHEAT,
                accountId,
                profileId,
                rvn,
                command,
                body
        );

        return Response.ok(response).header("X-Epicgames-Profile-Revision", response.getProfileRevision()).build();
    }
}