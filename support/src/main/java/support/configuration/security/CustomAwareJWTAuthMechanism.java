package support.configuration.security;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CustomAwareJWTAuthMechanism implements HttpAuthenticationMechanism {

    protected static final String AUTHORIZATION_HEADER = "Authorization";

    protected static final String BEARER = "Bearer";

    @Inject
    JwtUtils jwtUtils;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        HttpServerRequest request = context.request();
        String jwt = parseJwt(request);

        if (request.path()
                .startsWith("/arxcess-erp-api/auth") || request.path()
                .contains("/system/currency") || request.path()
                .contains("/arxcess-erp-api/workshop/login") || request.path()
                .contains("/system/timezone") || request.path()
                .contains("/file") || request.path()
                .contains("/status") || request.path()
                .contains("/swagger") || request.path()
                .contains("/dev")) {

            return Uni.createFrom()
                    .optional(Optional.empty());

        } else {

            if (jwt != null) {
                if (jwtUtils.validateJwtToken(jwt)) {

                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    Set<String> userRoles = new HashSet<>();

                    QuarkusSecurityIdentity identity = QuarkusSecurityIdentity.builder()
                            .setPrincipal(new QuarkusPrincipal(username))
                            .addRoles(userRoles)
                            .build();

                    return Uni.createFrom()
                            .item(identity);

                }

                return Uni.createFrom()
                        .failure(new AuthenticationFailedException());

            }

            return Uni.createFrom()
                    .failure(new AuthenticationFailedException());

        }
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        ChallengeData result = new ChallengeData(HttpResponseStatus.UNAUTHORIZED.code(),
                HttpHeaderNames.WWW_AUTHENTICATE, "Bearer {token}");
        return Uni.createFrom()
                .item(result);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(TokenAuthenticationRequest.class);
    }

    private String parseJwt(HttpServerRequest request) {

        String headerAuth = request.getHeader(AUTHORIZATION_HEADER);

        if (headerAuth != null && headerAuth.startsWith(BEARER)) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
