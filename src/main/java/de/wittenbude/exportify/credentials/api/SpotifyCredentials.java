package de.wittenbude.exportify.credentials.api;

import de.wittenbude.exportify.user.api.ExportifyUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class SpotifyCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    private String tokenType;
    private Instant expiresAt;

    @Column(length = 1024)
    private String scope;

    @Column(length = 1024)
    private String accessToken;

    @Column(length = 1024)
    private String refreshToken;

    @OneToOne(orphanRemoval = true)
    @JoinColumn
    private ExportifyUser exportifyUser;
}
