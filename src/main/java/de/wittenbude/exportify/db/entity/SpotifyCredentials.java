package de.wittenbude.exportify.db.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class SpotifyCredentials {
    private String spotifyUserID;
    private String accessToken;
    private String tokenType;
    private String scope;
    private Instant expiresAt;
    private String refreshToken;
}
