package support.ticket.auth.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import support.configuration.handler.ResponseMessage;
import support.statics.StatusTypes;
import support.ticket.auth.service.SupportAgentService;
import support.ticket.auth.service.payload.SupportAgentRequest;
import support.ticket.domains.User;

@Path("management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Management - User Management", description = "User Management")
public class SupportAgentController {

    @Inject
    SupportAgentService service;

    @POST
    @Path("create")
    @Operation(summary = "Create Agent X", description = "Create support Agent")
    @APIResponse(description = "Successful", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response create(SupportAgentRequest request, @Context SecurityContext ctx){
        return Response.ok(new ResponseMessage(StatusTypes.CREATED.label,service.create(request) )).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Get Agent X by Id", description = "Get support Agent by Id")
    @APIResponse(description = "Successful", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response getById(@PathParam("id") Long id){
        return Response.ok(new ResponseMessage(StatusTypes.CREATED.label,service.getById(id) )).build();
    }
}
