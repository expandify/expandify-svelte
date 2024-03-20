package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Credentials;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class SpotifyTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;


    public Credentials convert() {
        return Credentials
                .builder()
                .accessToken(accessToken)
                .tokenType(tokenType)
                .scope(scope)
                .expiresAt(Instant.now().plus(expiresIn, ChronoUnit.SECONDS))
                .refreshToken(refreshToken)
                .build();
    }
}
