package de.wittenbude.exportify.domain.entities;

import de.wittenbude.exportify.domain.valueobjects.Image;
import de.wittenbude.exportify.domain.valueobjects.PlaylistTrack;
import de.wittenbude.exportify.domain.valueobjects.SpotifyObjectType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Playlist {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    private Boolean collaborative;
    private String description;
    private Integer followers;
    private String href;
    private String spotifyID;
    private String name;
    private String ownerPublicUserSpotifyID;
    private Boolean publicAccess;
    private String spotifySnapshotId;
    private Integer totalTracks;
    private String uri;

    @Enumerated(EnumType.STRING)
    private SpotifyObjectType spotifyObjectType;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    @ElementCollection
    private List<PlaylistTrack> tracks;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Playlist playlist = (Playlist) o;
        return getId() != null && Objects.equals(getId(), playlist.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

