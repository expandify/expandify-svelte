package de.wittenbude.exportify.db.entity;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Followers;
import de.wittenbude.exportify.models.Image;
import de.wittenbude.exportify.models.ObjectType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class ArtistEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> externalUrls;

    @JdbcTypeCode(SqlTypes.JSON)
    private Followers followers;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> genres;

    private String href;

    @Column(unique = true)
    private String spotifyID;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> images;

    private String name;

    private Integer popularity;

    private ObjectType type;

    private String uri;


    public static ArtistEntity from(Artist artist) {
        return ArtistEntity
                .builder()
                .id(artist.getId())
                .externalUrls(artist.getExternalUrls())
                .followers(artist.getFollowers())
                .genres(artist.getGenres())
                .href(artist.getHref())
                .spotifyID(artist.getSpotifyID())
                .images(artist.getImages())
                .name(artist.getName())
                .popularity(artist.getPopularity())
                .type(artist.getType())
                .uri(artist.getUri())
                .build();
    }

    public Artist convert() {
        return Artist
                .builder()
                .id(id)
                .externalUrls(externalUrls)
                .followers(followers)
                .genres(genres)
                .href(href)
                .spotifyID(spotifyID)
                .images(images)
                .name(name)
                .popularity(popularity)
                .type(type)
                .uri(uri)
                .build();
    }

}
