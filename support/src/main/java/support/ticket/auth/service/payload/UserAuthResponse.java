package support.ticket.auth.service.payload;

public class UserAuthResponse {

    public String jwt;

    public UserAuthResponse() {
    }

    public UserAuthResponse(String jwt) {
        this.jwt = jwt;
    }

}
