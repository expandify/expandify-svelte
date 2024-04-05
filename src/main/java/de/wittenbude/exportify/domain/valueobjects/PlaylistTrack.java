package de.wittenbude.exportify.domain.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@Embeddable
@EqualsAndHashCode
public class PlaylistTrack {

    private Instant addedAt;
    private String addedByPublicUserSpotifyID;
    private Boolean isLocal;
    private String trackSpotifyID;

    @Enumerated(EnumType.STRING)
    private SpotifyObjectType spotifyObjectType;
}
