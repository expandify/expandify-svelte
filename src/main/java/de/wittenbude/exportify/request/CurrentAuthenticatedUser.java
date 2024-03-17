package de.wittenbude.exportify.request;

import de.wittenbude.exportify.services.AuthenticationService;
import lombok.Getter;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.UUID;

@RequestScope
@Component
@Getter
public class CurrentAuthenticatedUser {
    private final UUID userID;
    private final Jwt jwt;
    private final UUID credentialsID;

    public CurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
        }

        if (!(jwtAuthenticationToken.getDetails() instanceof Map)) {
            throw new ProviderNotFoundException("Authentication Details are not a map");
        }

        //TODO Casting
        Map<String, String> details = (Map<String, String>) jwtAuthenticationToken.getDetails();

        this.userID = UUID.fromString(details.get(AuthenticationService.USER_ID_CLAIM));
        this.credentialsID = UUID.fromString(details.get(AuthenticationService.USER_CREDENTIALS_CLAIM));
        this.jwt = jwtAuthenticationToken.getToken();
    }
}
