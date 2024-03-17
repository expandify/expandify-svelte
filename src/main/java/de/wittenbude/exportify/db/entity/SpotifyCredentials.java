package de.wittenbude.exportify.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class SpotifyCredentials {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(length = 1024)
    private String accessToken;

    private String tokenType;

    @Column(length = 1024)
    private String scope;

    private Instant expiresAt;

    @Column(length = 1024)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    private SpotifyUser spotifyUser;
}
