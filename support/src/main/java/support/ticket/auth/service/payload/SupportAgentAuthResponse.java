package support.ticket.auth.service.payload;

import support.ticket.domains.User;

public class SupportAgentAuthResponse {

    public User user;

    public String jwt;

    public SupportAgentAuthResponse() {
    }

    public SupportAgentAuthResponse(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }
}
