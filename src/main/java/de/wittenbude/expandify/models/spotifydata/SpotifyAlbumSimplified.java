package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyImage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.AlbumGroup;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "albumsSimplified")
public class SpotifyAlbumSimplified {
    private AlbumGroup albumGroup;
    private AlbumType albumType;
    private ArtistSimplified[] artists;
    private CountryCode[] availableMarkets;
    private Map<String, String> externalUrls;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private SpotifyImage[] images;
    private String name;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    private String restrictionReason;
    private ModelObjectType type;
    private String uri;

    public SpotifyAlbumSimplified(AlbumSimplified albumSimplified) {
        this.albumGroup = albumSimplified.getAlbumGroup();
        this.albumType = albumSimplified.getAlbumType();
        this.artists = albumSimplified.getArtists();
        this.availableMarkets = albumSimplified.getAvailableMarkets();
        this.externalUrls = albumSimplified.getExternalUrls().getExternalUrls();
        this.href = albumSimplified.getHref();
        this.id = albumSimplified.getId();
        this.images = Arrays.stream(albumSimplified.getImages()).map(SpotifyImage::new).toArray(SpotifyImage[]::new);
        this.name = albumSimplified.getName();
        this.releaseDate = albumSimplified.getReleaseDate();
        this.releaseDatePrecision = albumSimplified.getReleaseDatePrecision();
        this.restrictionReason = albumSimplified.getRestrictions().getReason();
        this.type = albumSimplified.getType();
        this.uri = albumSimplified.getUri();
    }
}
