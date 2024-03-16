package de.wittenbude.exportify.request;

import de.wittenbude.exportify.db.entity.SpotifyCredentials;
import de.wittenbude.exportify.db.repositories.SpotifyCredentialsRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.spotify.data.TokenResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequestScope
@Component
@Getter
public class CurrentAccessCredentials {

    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final SpotifyCredentialsRepository spotifyCredentialsRepository;

    private SpotifyCredentials spotifyCredentials;

    public CurrentAccessCredentials(SpotifyAuthenticationClient spotifyAuthenticationClient,
                                    SpotifyCredentialsRepository spotifyCredentialsRepository,
                                    CurrentAuthenticatedUser currentAuthenticatedUser) {
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.spotifyCredentialsRepository = spotifyCredentialsRepository;

        this.spotifyCredentials = spotifyCredentialsRepository
                .getBySpotifyUserID(currentAuthenticatedUser.getUserId())
                .orElse(null);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(spotifyCredentials.getExpiresAt());
    }

    public void refreshCredentials() {
        TokenResponse tokenResponse = spotifyAuthenticationClient.refresh(SpotifyAuthenticationClient
                .RefreshTokenForm
                .builder()
                .refreshToken((spotifyCredentials.getRefreshToken()))
                .build());

        this.spotifyCredentials = SpotifyCredentials
                .builder()
                .accessToken(tokenResponse.getAccessToken())
                .tokenType(tokenResponse.getTokenType())
                .scope(tokenResponse.getScope())
                .expiresAt(Instant.now().plus(tokenResponse.getExpiresIn(), ChronoUnit.SECONDS))
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }
}
