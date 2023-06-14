package support.ticket.auth.service.payload;

import jakarta.json.bind.annotation.JsonbTransient;

public class UserAuthRequest {

    public String email;

    public String applicationType;

    public String token;

    @JsonbTransient
    public String getBearerToken(){
        return "Bearer "+token;
    }
}
