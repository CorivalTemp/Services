package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.exceptions.fortnite.InvalidFeatureKeyException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.TemplateNotFound;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.items.Template;

@Path("/api/game/v2")
@Produces(MediaType.APPLICATION_JSON)
public class TemplatesController {

    @GET
    @Path("/template/{templateId}")
    @OAuth(resource = "s2s:templates", action = Actions.READ)
    public Response getTemplateById(@PathParam(value = "templateId") String templateId) {
        Template template = TemplateManager.getTemplate(templateId);
        if (template == null) throw new TemplateNotFound(templateId);
        return Response.ok(template).build();
    }

    @GET
    @Path("/templates/{templatesName}")
    @OAuth(resource = "s2s:templates", action = Actions.READ)
    public Response getTemplates(@PathParam(value = "templatesName") String templatesName) {
        return Response.ok(TemplateManager.grabTemplatesByName(templatesName)).build();
    }

    @GET
    @Path("/enabled_features")
    @OAuth()
    public Response getEnabledFeatures() {
        return Response.ok(new Object[0]).build();
    }

    @GET
    @Path("/clientfeaturekeys/{accountId}")
    @OAuth()
    public Response clientfeaturekeys() throws InvalidFeatureKeyException {
        throw new InvalidFeatureKeyException();
    }
}
