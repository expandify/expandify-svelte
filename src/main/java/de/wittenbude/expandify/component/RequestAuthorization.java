package de.wittenbude.expandify.component;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.repositories.SpotifyApiCredentialRepository;
import de.wittenbude.expandify.services.auth.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

@Component
public class RequestAuthorization {

    private final JwtService jwtService;
    private final SpotifyApiCredentialRepository spotifyApiCredentialRepository;

    public RequestAuthorization(
            JwtService jwtService,
            SpotifyApiCredentialRepository spotifyApiCredentialRepository
    ) {
        this.jwtService = jwtService;
        this.spotifyApiCredentialRepository = spotifyApiCredentialRepository;
    }


    public String spotifyUserId() throws UnauthorizedException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt) {
            JwtService.JwtClaims claims = this.jwtService.getClaims((Jwt) principal);
            return claims.getUserId();
        }
        throw new UnauthorizedException("User not authorized");
    }

    public SpotifyApiCredential spotifyApiCredential() throws UnauthorizedException {
        return spotifyApiCredentialRepository
                .findById(spotifyUserId())
                .orElseThrow(() -> new UnauthorizedException("User does not spotifyApiCredentials in the database"));
    }

    public SpotifyApi spotifyApi() throws UnauthorizedException {
        SpotifyApiCredential spotifyApiCredential = spotifyApiCredential();
        return new SpotifyApi.Builder()
                        .setAccessToken(spotifyApiCredential.getAccessToken())
                        .setRefreshToken(spotifyApiCredential.getRefreshToken())
                        .build();

    }




}
