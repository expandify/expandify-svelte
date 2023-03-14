package de.wittenbude.expandify.requestscope;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.repositories.SpotifyApiCredentialRepository;
import de.wittenbude.expandify.services.auth.JwtService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

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
            JwtService.JwtClaims claims = jwtService.getClaims((Jwt) principal);
            return claims.getUserId();
        }
        return null;
    }

}
