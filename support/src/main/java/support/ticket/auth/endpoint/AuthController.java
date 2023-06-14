package support.ticket.auth.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import support.ticket.auth.service.AuthService;
import support.ticket.auth.service.payload.SupportAgentAuthRequest;
import support.ticket.auth.service.payload.SupportAgentAuthResponse;
import support.ticket.auth.service.payload.UserAuthRequest;
import support.ticket.auth.service.payload.UserAuthResponse;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Auth - Authorization", description = "Auth process")
public class AuthController {

    @Inject
    AuthService service;

    @POST
    @Path("user-login")
    @Operation(summary = "Login", description = "User (Customer) login")
    @APIResponse(description = "Successful", responseCode = "200", content = @Content(schema = @Schema(implementation = UserAuthResponse.class)))
    public Response remoteLogin(UserAuthRequest request) {
        return service.auth(request);
    }

    @POST
    @Path("agent-login")
    @Operation(summary = "Login", description = "Agent (Support) login")
    @APIResponse(description = "Successful", responseCode = "200", content = @Content(schema = @Schema(implementation = SupportAgentAuthResponse.class)))
    public Response login(SupportAgentAuthRequest request) {
        SupportAgentAuthResponse response = service.login(request);
        return Response.ok(response).build();
    }
}
