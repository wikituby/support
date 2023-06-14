package support.configuration.security;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtSignatureException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.ticket.auth.service.payload.UserAuthRequest;
import support.ticket.domains.User;

import java.util.Date;
@ApplicationScoped
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final String jwtSecret = "z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)"
            + "H+MbQeThWmZq4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWnZ";

    private final int jwtExpirationMs = 86400000;

    @Inject
    JWTParser parser;

    @Inject
    JsonWebToken jwt;

    public String generateJwtToken(User user) {

        return Jwt.subject(user.email).issuedAt(new Date().toInstant())
                .expiresAt(new Date((new Date()).getTime() + jwtExpirationMs).toInstant())
                .upn(user.username).issuer("support server").signWithSecret(jwtSecret);

    }

    public String generateJwtToken(UserAuthRequest request) {

        return Jwt.subject(request.email).issuedAt(new Date().toInstant())
                .expiresAt(new Date((new Date()).getTime() + 3600).toInstant())
                .upn(request.applicationType).issuer("support server").signWithSecret(jwtSecret);

    }

    public String getUserNameFromJwtToken(String token) {

        try {
            jwt = parser.verify(token, jwtSecret);

            return jwt.getSubject();

        } catch (ParseException e) {
            logger.error("Parse Exception: {}", e.getMessage());
            throw new WebApplicationException("Access Denied.", 401);

        } catch (Exception e) {
            logger.error("Null Exception: {}", e.getMessage());
            throw new WebApplicationException("Access Denied.", 401);
        }

    }

    public boolean validateJwtToken(String authToken) {

        try {
            jwt = parser.verify(authToken, jwtSecret);

            return true;

        } catch (JwtSignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            return false;

        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            return false;

        } catch (ParseException e) {
            logger.error("Parse Exception: {}", e.getMessage());
            return false;

        } catch (Exception e) {
            logger.error("Null Exception: {}", e.getMessage());
            return false;
        }

    }
}
