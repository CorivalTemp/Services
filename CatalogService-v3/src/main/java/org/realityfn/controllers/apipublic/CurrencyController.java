package org.realityfn.controllers.apipublic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.errorhandling.catalog.CurrencyNotFoundException;
import org.realityfn.errorhandling.catalog.InvalidCountException;
import org.realityfn.errorhandling.catalog.InvalidStartException;
import org.realityfn.models.DTO.CatalogCurrencyDTO;

import org.realityfn.models.mongo.CatalogCurrencyMongo;
import org.realityfn.models.paging.PagedResults;
import org.realityfn.utils.database.CurrenciesDAO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Path("/api/shared")
public class CurrencyController {

    private final CurrenciesDAO currenciesDAO;

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public CurrencyController() {
        this.currenciesDAO = new CurrenciesDAO();
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/currencies")
    @Produces("application/json")
    @OAuth(resource = "catalog:shared:currencies", action = Actions.READ)
    public Response getAllCurrencies(
            @QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("count") @DefaultValue("10") int count
    ) {
        if (start < 0) {
            throw new InvalidStartException(start);
        }
        if (count > 1000 || count <= 0) {
            throw new InvalidCountException(count);
        }
        List<CatalogCurrencyDTO> currencies = currenciesDAO.getCurrenciesPerCount(start, count);
        PagedResults results = new PagedResults(
                currencies,
                start,
                count,
                currenciesDAO.getAllFields().size()
        );
        return Response.ok(results).build();
    }

    @GET
    @Path("/currencies/{currencyId}")
    @Produces("application/json")
    @OAuth(resource = "catalog:shared:currencies", action = Actions.READ)
    public Response getCurrencyByCode(
            @PathParam("currencyId") String currencyId
    ) {
        Optional<CatalogCurrencyMongo> currencyMongoOpt = currenciesDAO.getCurrencyByCode(currencyId);
        if (currencyMongoOpt.isEmpty()) {
            throw new CurrencyNotFoundException(currencyId);
        }
        CatalogCurrencyDTO results = currenciesDAO.toDTO(currencyMongoOpt.get());
        return Response.ok(results).build();
    }
}