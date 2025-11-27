package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.services.FortniteService;
import org.realityfn.fortnite.core.services.ItemShopService;

import java.util.List;

@Path("/api/storefront/")
public class StorefrontController {
    private final FortniteService fortniteService;
    private final ItemShopService itemShopService;

    @Inject
    public StorefrontController(FortniteService fortniteService, ItemShopService itemShopService) {
        this.fortniteService = fortniteService;
        this.itemShopService = itemShopService;
    }

    @GET
    @Path("v2/keychain")
    @Produces(MediaType.APPLICATION_JSON)
    @OAuth(resource = "storefront", action = Actions.READ)
    public Response getCurrentKeyChain(@QueryParam(value = "numKeysDownloaded") String numKeysDownloaded) {
        List<String> keys = fortniteService.getKeychain();
        return Response.ok(keys).build();
    }

    @GET
    @Path("v2/catalog")
    @OAuth(resource = "storefront", action = Actions.READ)
    @Produces(MediaType.APPLICATION_JSON)
    public Response catalog() {
        return Response.ok(itemShopService.processItemShop()).build();
    }
}