package support.ticket.auth.service.payload;

import io.smallrye.common.constraint.NotNull;

public class SupportAgentAuthRequest {

    @NotNull
    public String username;

    @NotNull
    public String password;
}
