package support.ticket.auth.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import support.ticket.auth.service.payload.SupportAgentRequest;
import support.ticket.domains.User;
import support.ticket.domains.repository.UserRepository;

@ApplicationScoped
public class SupportAgentService {

    @Inject
    UserRepository userRepository;

    public User create(SupportAgentRequest request){

        String password = BcryptUtil.bcryptHash(request.password);

        User user = new User();
        user.username = request.username;
        user.email = request.email;
        user.role = request.role;
        user.password = password;
        user.persist();

        return user;

    }

    public User getById(Long id){
        return userRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Not found",404));
    }
}
