package de.wittenbude.exportify.track.api;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;
    private String href;
    private String spotifyID;
    private Boolean isPlayable;
    private String restrictions;
    private String name;
    private String previewUrl;
    private Integer trackNumber;
    private String spotifyObjectType;
    private String uri;
    private Boolean isLocal;
    private ExternalIDs externalIDs;
    private Integer popularity;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<CountryCode> availableMarkets;

    private String spotifyAlbumID;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> spotifyArtistIDs;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Track track = (Track) o;
        return getId() != null && Objects.equals(getId(), track.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @Embeddable
    @EqualsAndHashCode
    public static class ExternalIDs {

        private String isrc;
        private String ean;
        private String upc;

    }
}