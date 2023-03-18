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
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "albums")
public class SpotifyAlbum extends SpotifyAlbumSimplified{
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
    private SpotifyTrack[] spotifyTracks;
    private ModelObjectType type;
    private String uri;

    public SpotifyAlbum(SavedAlbum savedAlbum) {
        this.albumType = savedAlbum.getAlbum().getAlbumType();
        this.artists = savedAlbum.getAlbum().getArtists();
        this.availableMarkets = savedAlbum.getAlbum().getAvailableMarkets();
        this.copyrights = Arrays.stream(savedAlbum.getAlbum().getCopyrights()).map(SpotifyCopyright::new).toArray(SpotifyCopyright[]::new);
        this.externalIds = savedAlbum.getAlbum().getExternalIds().getExternalIds();
        this.externalUrls = savedAlbum.getAlbum().getExternalUrls().getExternalUrls();
        this.genres = savedAlbum.getAlbum().getGenres();
        this.href = savedAlbum.getAlbum().getHref();
        this.id = savedAlbum.getAlbum().getId();
        this.images = Arrays.stream(savedAlbum.getAlbum().getImages()).map(SpotifyImage::new).toArray(SpotifyImage[]::new);
        this.label = savedAlbum.getAlbum().getLabel();
        this.name = savedAlbum.getAlbum().getName();
        this.popularity = savedAlbum.getAlbum().getPopularity();
        this.releaseDate = savedAlbum.getAlbum().getReleaseDate();
        this.releaseDatePrecision = savedAlbum.getAlbum().getReleaseDatePrecision();
        this.spotifyTracks = null; // TODO
        this.type = savedAlbum.getAlbum().getType();
        this.uri = savedAlbum.getAlbum().getUri();
    }
}
