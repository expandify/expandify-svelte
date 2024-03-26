package de.wittenbude.exportify.models;

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
public class Credentials {
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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, unique = true)
    private PrivateUser user;
}
