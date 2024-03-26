package de.wittenbude.exportify.services;

import de.wittenbude.exportify.jwt.JweEncoder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiTokenService {

    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;
    private final JweEncoder jweEncoder;

    public ApiTokenService(JweEncoder jweEncoder) {
        this.jweEncoder = jweEncoder;
    }

    public String createApiToken(UUID userID) {
        return jweEncoder.encode(USER_ID_CLAIM, userID);
    }

    public AbstractAuthenticationToken parseApiToken(Jwt jwt) {
        String userID = jwt.getClaimAsString(USER_ID_CLAIM);
        return new JwtAuthenticationToken(jwt, null, userID);
    }

    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
        }

        return UUID.fromString(jwtAuthenticationToken.getName());
    }


}
