package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.fortnite.core.enums.VersionCheck;
import org.realityfn.fortnite.core.exceptions.fortnite.InvalidFeatureKeyException;
import org.realityfn.fortnite.core.models.fortnite.VersionCheckModel;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class GameServiceController {

    @GET
    @Path("/entitlementCheck")
    @OAuth()
    public Response entitlementCheck() {
        return Response.noContent().build();
    }

    @GET
    @Path("/v2/versioncheck/{platform}")
    @OAuth()
    public Response checkVersion(@PathParam("platform") String platform, @QueryParam("version") String version) {
        return Response.ok(new VersionCheckModel(VersionCheck.NO_UPDATE)).build();
    }
}