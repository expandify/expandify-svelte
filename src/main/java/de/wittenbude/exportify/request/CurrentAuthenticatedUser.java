package de.wittenbude.exportify.request;

import lombok.Getter;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@Getter
public class CurrentAuthenticatedUser {
    private final String userId;
    private final Jwt jwt;

    public CurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            this.userId = jwtAuthenticationToken.getName();
            this.jwt = jwtAuthenticationToken.getToken();
            return;
        }

        throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
    }
}
