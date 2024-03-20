package de.wittenbude.exportify.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Credentials {
    private UUID id;
    private String accessToken;
    private String tokenType;
    private String scope;
    private Instant expiresAt;
    private String refreshToken;
    private PrivateUser spotifyPrivateUser;
}
