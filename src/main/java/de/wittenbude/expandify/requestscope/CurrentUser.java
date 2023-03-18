package de.wittenbude.expandify.requestscope;

import de.wittenbude.expandify.services.auth.JwtService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class CurrentUser {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentUser.class);
    @Getter
    private final String spotifyUserId;

    public CurrentUser(JwtService jwtService) {
        this.spotifyUserId = spotifyUserId(jwtService);
    }


    private String spotifyUserId(JwtService jwtService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) { return null; }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            return jwtService.getUserIdFromJwt((Jwt) principal);
        }
        return null;
    }

}
