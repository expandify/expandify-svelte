package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyCopyright;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyImage;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "albums")
public class SpotifyAlbum {
    private AlbumType albumType;
    private ArtistSimplified[] artists;
    private CountryCode[] availableMarkets;
    private SpotifyCopyright[] copyrights;
    private Map<String, String> externalIds;
    private Map<String, String> externalUrls;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private SpotifyImage[] images;
    private String label;
    private String name;
    private Integer popularity;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    @DocumentReference(lazy = true)
    private List<SpotifyTrackSimplified> tracksSimplified;
    private ModelObjectType type;
    private String uri;

    public SpotifyAlbum(Album album, List<SpotifyTrackSimplified> tracks) {
        this.albumType = album.getAlbumType();
        this.artists = album.getArtists();
        this.availableMarkets = album.getAvailableMarkets();
        this.copyrights = Arrays.stream(album.getCopyrights()).map(SpotifyCopyright::new).toArray(SpotifyCopyright[]::new);
        this.externalIds = album.getExternalIds().getExternalIds();
        this.externalUrls = album.getExternalUrls().getExternalUrls();
        this.genres = album.getGenres();
        this.href = album.getHref();
        this.id = album.getId();
        this.images = Arrays.stream(album.getImages()).map(SpotifyImage::new).toArray(SpotifyImage[]::new);
        this.label = album.getLabel();
        this.name = album.getName();
        this.popularity = album.getPopularity();
        this.releaseDate = album.getReleaseDate();
        this.releaseDatePrecision = album.getReleaseDatePrecision();
        this.tracksSimplified = tracks;
        this.type = album.getType();
        this.uri = album.getUri();
    }


}
