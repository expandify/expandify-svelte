package de.wittenbude.expandify.requestscope;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.repositories.SpotifyApiCredentialRepository;
import de.wittenbude.expandify.services.auth.JwtService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequestScope
public class AuthenticatedUserData {

    // private static final Logger LOG = LoggerFactory.getLogger(AuthenticatedUserData.class);

    private final SpotifyApiCredentialRepository spotifyApiCredentialRepository;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    @Getter
    private SpotifyApiCredential credentials;
    @Getter
    private SpotifyApi authenticatedSpotifyApi;
    @Getter
    private final String spotifyUserId;


    public AuthenticatedUserData(
            @Value("${spotify.client-id}") String clientId,
            @Value("${spotify.client-secret}") String clientSecret,
            @Value("${spotify.redirect-uri}") String redirectUri,
            SpotifyApiCredentialRepository spotifyApiCredentialRepository,
            JwtService jwtService) {
        this.spotifyApiCredentialRepository = spotifyApiCredentialRepository;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.spotifyUserId = spotifyUserId(jwtService);
        this.credentials = credentials(this.spotifyUserId);
        this.authenticatedSpotifyApi = spotifyApi();

    }



    public void refresh(SpotifyApiCredential newCredentials) {
        this.credentials = newCredentials;
        this.authenticatedSpotifyApi = spotifyApi();
    }

    public boolean isExpired() {
        if (this.credentials == null ) { return false; }

        Date nowMinusFiveMin = Date.from(Instant.now().minus(5, ChronoUnit.MINUTES));
        return this.credentials.getExpiresAt().before(nowMinusFiveMin);
    }

    private SpotifyApiCredential credentials(String id) {
        if (id == null) { return null; }

        return spotifyApiCredentialRepository.findById(id).orElse(null);
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


    private SpotifyApi spotifyApi() {
        SpotifyApi.Builder spotifyApiBuilder = new SpotifyApi
                .Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .setRedirectUri(URI.create(this.redirectUri));

        if (this.credentials != null) {
            spotifyApiBuilder
                    .setAccessToken(this.credentials.getAccessToken())
                    .setRefreshToken(this.credentials.getRefreshToken());
        }
        return spotifyApiBuilder.build();
    }


}
