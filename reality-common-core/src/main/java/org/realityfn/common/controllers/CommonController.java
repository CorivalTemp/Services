package org.realityfn.common.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.utils.AdminAccess;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Path("/api")
public class CommonController {

    @Context
    private ContainerRequestContext requestContext;

    @GET
    @Path("/version")
    @Produces("application/json")
    public Response getJson() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/config/version.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(inputStream);

        ObjectNode mutableNode = jsonNode.deepCopy();
        mutableNode.put("serverDate", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        mutableNode.put("app", AdminAccess.fetchCurrentAppName(requestContext));
        return Response.ok(mutableNode).build();
    }
}