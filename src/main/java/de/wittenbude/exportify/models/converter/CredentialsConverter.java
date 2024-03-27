package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CredentialsConverter {


    public static Credentials from(SpotifyTokenResponse spotifyTokenResponse) {
        return new Credentials()
                .setAccessToken(spotifyTokenResponse.getAccessToken())
                .setTokenType(spotifyTokenResponse.getTokenType())
                .setScope(spotifyTokenResponse.getScope())
                .setExpiresAt(Instant.now().plus(spotifyTokenResponse.getExpiresIn(), ChronoUnit.SECONDS))
                .setRefreshToken(spotifyTokenResponse.getRefreshToken());
    }
}
