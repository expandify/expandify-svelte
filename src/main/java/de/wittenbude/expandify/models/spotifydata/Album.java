package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.Copyright;
import de.wittenbude.expandify.models.spotifydata.helper.Image;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "albums")
public class Album {
    private AlbumType albumType;
    @DocumentReference(lazy = true)
    private List<ArtistSimplified> artists;
    private CountryCode[] availableMarkets;
    private Copyright[] copyrights;
    private Map<String, String> externalIds;
    private Map<String, String> externalUrls;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private String label;
    private String name;
    private Integer popularity;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    @DocumentReference(lazy = true)
    private List<TrackSimplified> tracks;
    private ModelObjectType type;
    private String uri;

    public Album(se.michaelthelin.spotify.model_objects.specification.Album album, List<TrackSimplified> tracks) {
        this.albumType = album.getAlbumType();
        this.artists = Arrays.stream(album.getArtists()).map(ArtistSimplified::new).toList();
        this.availableMarkets = album.getAvailableMarkets();
        this.copyrights = Arrays.stream(album.getCopyrights()).map(Copyright::new).toArray(Copyright[]::new);
        this.externalIds = album.getExternalIds().getExternalIds();
        this.externalUrls = album.getExternalUrls().getExternalUrls();
        this.genres = album.getGenres();
        this.href = album.getHref();
        this.id = album.getId();
        this.images = Arrays.stream(album.getImages()).map(Image::new).toArray(Image[]::new);
        this.label = album.getLabel();
        this.name = album.getName();
        this.popularity = album.getPopularity();
        this.releaseDate = album.getReleaseDate();
        this.releaseDatePrecision = album.getReleaseDatePrecision();
        this.type = album.getType();
        this.uri = album.getUri();
        this.tracks = tracks;
    }

}
