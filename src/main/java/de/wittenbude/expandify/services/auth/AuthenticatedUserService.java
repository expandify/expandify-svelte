package de.wittenbude.expandify.services.auth;

import de.wittenbude.expandify.models.db.SpotifyApiCredentials;
import de.wittenbude.expandify.repositories.users.SpotifyApiCredentialDao;
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
public class AuthenticatedUserService {

    // private static final Logger LOG = LoggerFactory.getLogger(AuthenticatedUserData.class);

    private final SpotifyApiCredentialDao spotifyApiCredentialDao;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    @Getter
    private SpotifyApiCredentials credentials;
    @Getter
    private SpotifyApi authenticatedSpotifyApi;
    @Getter
    private final String userId;


    public AuthenticatedUserService(
            @Value("${spotify.client-id}") String clientId,
            @Value("${spotify.client-secret}") String clientSecret,
            @Value("${spotify.redirect-uri}") String redirectUri,
            SpotifyApiCredentialDao spotifyApiCredentialDao,
            JwtService jwtService) {
        this.spotifyApiCredentialDao = spotifyApiCredentialDao;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.userId = spotifyUserId(jwtService);
        this.credentials = credentials(this.userId);
        this.authenticatedSpotifyApi = spotifyApi();

    }

    public void refreshCredentials(SpotifyApiCredentials newCredentials) {
        this.credentials = newCredentials;
        this.authenticatedSpotifyApi = spotifyApi();
    }

    public boolean isExpired() {
        if (this.credentials == null ) { return false; }

        Date nowMinusFiveMin = Date.from(Instant.now().minus(5, ChronoUnit.MINUTES));
        return this.credentials.getExpiresAt().before(nowMinusFiveMin);
    }

    private SpotifyApiCredentials credentials(String id) {
        if (id == null) { return null; }

        return spotifyApiCredentialDao.find(id).orElse(null);
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
