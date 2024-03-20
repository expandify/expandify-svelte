package de.wittenbude.exportify.db.entity;

import de.wittenbude.exportify.models.Credentials;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class CredentialsEntity {

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
    private UserEntity userEntity;



    public static CredentialsEntity from(Credentials credentials) {
        return CredentialsEntity
                .builder()
                .id(credentials.getId())
                .accessToken(credentials.getAccessToken())
                .tokenType(credentials.getTokenType())
                .scope(credentials.getScope())
                .expiresAt(credentials.getExpiresAt())
                .refreshToken(credentials.getRefreshToken())
                .userEntity(UserEntity.from(credentials.getSpotifyPrivateUser()))
                .build();
    }

    public Credentials convert() {
        return Credentials
                .builder()
                .id(id)
                .accessToken(accessToken)
                .tokenType(tokenType)
                .scope(scope)
                .expiresAt(expiresAt)
                .refreshToken(refreshToken)
                .spotifyPrivateUser(userEntity.convert())
                .build();
    }

}
