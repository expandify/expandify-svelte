package de.wittenbude.expandify.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.time.Instant;
import java.util.Date;

@Data
@Document(collection = "spotify-api-credentials")
public class SpotifyApiCredential {

    @Id
    private String id;
    private String accessToken;
    private String tokenType;
    private String scope;
    private Date expiresAt;
    private String refreshToken;

    public SpotifyApiCredential(AuthorizationCodeCredentials credentials, String id) {
        this.id = id;
        this.accessToken = credentials.getAccessToken();
        this.tokenType = credentials.getTokenType();
        this.expiresAt = Date.from(Instant.now().plusSeconds(credentials.getExpiresIn()));
        this.scope = credentials.getScope();
        this.refreshToken = credentials.getRefreshToken();
    }
}
