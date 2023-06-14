package support.ticket.auth.service.payload;

import io.smallrye.common.constraint.NotNull;

public class SupportAgentRequest {

    @NotNull
    public String username;

    @NotNull
    public String email;

    @NotNull
    public String password;

    public String role;
}
