package de.wittenbude.exportify.models;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.embeds.Copyright;
import de.wittenbude.exportify.models.embeds.ExternalIDs;
import de.wittenbude.exportify.models.embeds.Image;
import de.wittenbude.exportify.models.embeds.ReleaseDatePrecision;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Album {

    @Id
    @GeneratedValue
    private UUID id;
    private String albumType;
    private Integer totalTracks;

    @ElementCollection
    private List<CountryCode> availableMarkets;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;
    private String href;

    @Column(unique = true)
    private String spotifyID;

    @ElementCollection
    private List<Image> images;

    private String name;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    private String restrictions;
    private String spotifyObjectType;
    private String uri;

    @ManyToMany
    @JoinTable
    private Set<Artist> artists;

    @OneToMany(mappedBy = "album", orphanRemoval = true)
    private List<Track> tracks;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Copyright> copyrights;

    private ExternalIDs externalIDs;

    @ElementCollection
    private List<String> genres;
    private String label;
    private Integer popularity;

    public Album setArtists(Set<Artist> artists) {
        if (this.artists == null) {
            this.artists = artists;
            return this;
        }
        this.artists.clear();
        this.artists.addAll(artists);
        return this;
    }

    public Album setTracks(List<Track> tracks) {
        if (this.tracks == null) {
            this.tracks = tracks;
            return this;
        }
        this.tracks.clear();
        this.tracks.addAll(tracks);
        return this;
    }

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

