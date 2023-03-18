package de.wittenbude.expandify.requestscope;

import de.wittenbude.expandify.models.SpotifyApiCredential;
import de.wittenbude.expandify.repositories.SpotifyApiCredentialRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequestScope
public class CurrentSpotifyData {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentSpotifyData.class);

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    @Getter
    private SpotifyApiCredential credentials;
    @Getter
    private SpotifyApi api;


    public CurrentSpotifyData(
            @Value("${spotify.client-id}") String clientId,
            @Value("${spotify.client-secret}") String clientSecret,
            @Value("${spotify.redirect-uri}") String redirectUri,
            SpotifyApiCredentialRepository spotifyApiCredentialRepository,
            CurrentUser currentUser) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.credentials = spotifyApiCredential(currentUser.getSpotifyUserId(), spotifyApiCredentialRepository);
        this.api = spotifyApi();
    }

    public void refresh(SpotifyApiCredential newCredentials) {
        this.credentials = newCredentials;
        this.api = spotifyApi();
    }


    private SpotifyApiCredential spotifyApiCredential(String spotifyUserId, SpotifyApiCredentialRepository spotifyApiCredentialRepository) {
        if (spotifyUserId == null) { return null; }

        return spotifyApiCredentialRepository
                .findById(spotifyUserId)
                .orElse(null);
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

    public boolean isExpired() {
        if (this.credentials == null ) { return false; }

        Date nowMinusFiveMin = Date.from(Instant.now().minus(5, ChronoUnit.MINUTES));
        return this.credentials.getExpiresAt().before(nowMinusFiveMin);
    }

}
