package org.realityfn.controllers.apipublic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.models.DTO.CatalogItemDTO;
import org.realityfn.utils.database.ItemsDAO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/shared/bulk")
public class BulkLookupController {

    private final ItemsDAO itemsDAO;

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public BulkLookupController() {
        this.itemsDAO = new ItemsDAO();
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/items")
    @Produces("application/json")
    @OAuth(resource = "catalog:shared:items", action = Actions.READ)
    public Response lookupItems(@QueryParam("id") List<String> itemIds) { // More stuff like DLC later when I feel like it.

        List<CatalogItemDTO> items = itemsDAO.getItemsByIds(itemIds);
        Map<String, CatalogItemDTO> results = new HashMap<>();
        for (CatalogItemDTO item : items) {
            results = new HashMap<>(
                Map.of(item.getId(), item)
            );
        }
        return Response.ok(results).build();
    }
    @GET
    @Path("/offers")
    @Produces("application/json")
    @OAuth(resource = "catalog:shared:offers", action = Actions.READ)
    public Response lookupOffers(@QueryParam("id") List<String> offerIds) { // More stuff like DLC later when I feel like it.

        List<CatalogItemDTO> items = itemsDAO.getItemsByIds(offerIds);
        Map<String, CatalogItemDTO> results = new HashMap<>();
        for (CatalogItemDTO item : items) {
            results = new HashMap<>(
                    Map.of(item.getId(), item)
            );
        }
        return Response.ok(results).build();
    }
}