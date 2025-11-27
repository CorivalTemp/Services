package org.realityfn.controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@Path("/api")
public class CommonController {
    @GET
    @Path("/version/test")
    @Produces("application/json")
    @OAuth(resource = "null", action = Actions.NONE)
    public Response getServiceVersionTest() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/config/version.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(inputStream);

        ObjectNode mutableNode = jsonNode.deepCopy();
        mutableNode.put("serverDate", Instant.now().toString());
        return Response.ok(mutableNode).build();
    }
}