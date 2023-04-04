package de.wittenbude.expandify.models.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "credentials")
public class SpotifyApiCredentials {

    @Id
    private String id;
    private String accessToken;
    private String tokenType;
    private String scope;
    private Date expiresAt;
    private String refreshToken;

    public SpotifyApiCredentials(AuthorizationCodeCredentials credentials, String id) {
        this.id = id;
        this.accessToken = credentials.getAccessToken();
        this.tokenType = credentials.getTokenType();
        this.expiresAt = Date.from(Instant.now().plusSeconds(credentials.getExpiresIn()));
        this.scope = credentials.getScope();
        this.refreshToken = credentials.getRefreshToken();
    }
}
