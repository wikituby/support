package support.ticket.auth.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import support.configuration.handler.ResponseMessage;
import support.configuration.security.JwtUtils;
import support.statics.AppTypes;
import support.ticket.auth.service.payload.SupportAgentAuthRequest;
import support.ticket.auth.service.payload.SupportAgentAuthResponse;
import support.ticket.auth.service.payload.UserAuthRequest;
import support.ticket.auth.service.payload.UserAuthResponse;
import support.ticket.domains.repository.UserRepository;
import support.ticket.restclient.AuthRestClient;

import java.net.URI;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.OK;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtUtils jwtUtils;


    public Response auth(UserAuthRequest request){

        AppTypes app = AppTypes.getEnum(request.applicationType);

        AuthRestClient restClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(app.label))
                .build(AuthRestClient.class);

        try(Response response = restClient.authenticate(request.getBearerToken());){
            if (response.getStatusInfo().equals(OK)) {
                //generate token for this user i guess before persisting
                String jwtToken = jwtUtils.generateJwtToken(request);
                return Response.ok().entity(new ResponseMessage(OK.getReasonPhrase(),new UserAuthResponse(jwtToken))).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }catch (Exception ex){
            return Response.status(Response.Status.FORBIDDEN).entity(new ResponseMessage(ex.getMessage())).build();
        }
    }

    public SupportAgentAuthResponse login(SupportAgentAuthRequest request){
        return Optional.ofNullable(userRepository.login(request.username, request.password))
                .map(user -> {
                    String jwtToken = jwtUtils.generateJwtToken(user);
                    return new SupportAgentAuthResponse(user,jwtToken);
                }).orElseThrow(() -> new WebApplicationException("Not Found",404));
    }
}
