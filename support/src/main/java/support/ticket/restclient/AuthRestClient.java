package support.ticket.restclient;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/support")
@RegisterRestClient
public interface AuthRestClient {

    @POST
    Response authenticate(@HeaderParam("token") String bearerToken);
}
