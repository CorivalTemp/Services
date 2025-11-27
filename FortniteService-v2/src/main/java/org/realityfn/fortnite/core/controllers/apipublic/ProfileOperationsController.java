package org.realityfn.fortnite.core.controllers.apipublic;

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

@Path("/api/game/v2/profile/{accountId}")
public class ProfileOperationsController  {

    final ProfileManager profileManager;

    @Inject
    public ProfileOperationsController(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Context
    private UriInfo uriInfo;

    @Context
    private ContainerRequestContext requestContext;

    @POST
    @Path("/client/{command}")
    @OAuth(resource = "profile:{accountId}:commands", action = Actions.ALL)
    @OAuth(resource = "fortnite_role:client", action = Actions.ALL)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeProfileCommandClient(
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
                ProfileRoute.CLIENT,
                accountId,
                profileId,
                rvn,
                command,
                body
        );
        
        return Response.ok(response).header("X-Epicgames-Profile-Revision", response.getProfileRevision()).build();
    }

    @POST
    @Path("/dedicated_server/{command}")
    @OAuth(resource = "profile:{accountId}:commands", action = Actions.ALL)
    @OAuth(resource = "fortnite_role:dedicated_server", action = Actions.ALL)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeProfileCommandDedicatedService(
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
                ProfileRoute.DEDICATED_SERVER,
                accountId,
                profileId,
                rvn,
                command,
                body
        );

        return Response.ok(response).header("X-Epicgames-Profile-Revision", response.getProfileRevision()).build();
    }

    @POST
    @Path("/public/{command}")
    @OAuth() // public doesn't take permissions
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeProfileCommandPublic(
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
                ProfileRoute.PUBLIC,
                accountId,
                profileId,
                rvn,
                command,
                body
        );

        return Response.ok(response).header("X-Epicgames-Profile-Revision", response.getProfileRevision()).build();
    }

    @POST
    @Path("/s2s/{command}")
    @OAuth(resource = "s2s:profile:{command}", action = Actions.ALL)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeProfileCommandS2s(
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
                ProfileRoute.ALL,
                accountId,
                profileId,
                rvn,
                command,
                body
        );

        return Response.ok(response).header("X-Epicgames-Profile-Revision", response.getProfileRevision()).build();
    }
}
