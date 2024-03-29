package de.wittenbude.exportify.models;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.embeds.Copyright;
import de.wittenbude.exportify.models.embeds.ExternalIDs;
import de.wittenbude.exportify.models.embeds.Image;
import de.wittenbude.exportify.models.embeds.ReleaseDatePrecision;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Album {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private Instant versionTimestamp;

    private String albumType;
    private Integer totalTracks;
    private String href;
    private String spotifyID;
    private String name;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    private String restrictions;
    private String spotifyObjectType;
    private String uri;
    private ExternalIDs externalIDs;
    private String label;
    private Integer popularity;

    @JdbcTypeCode(SqlTypes.JSON)
    private Collection<Copyright> copyrights;

    @JdbcTypeCode(SqlTypes.JSON)
    private Collection<Image> images;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private Collection<CountryCode> availableMarkets;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private Collection<String> genres;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> spotifyArtistIDs;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> spotifyTrackIDs;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Album album = (Album) o;
        return getId() != null && Objects.equals(getId(), album.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

