package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.AlbumGroup;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "albumsSimplified")
public class AlbumSimplified {
    private AlbumGroup albumGroup;
    private AlbumType albumType;
    @DocumentReference(lazy = true)
    private List<ArtistSimplified> artists;
    private CountryCode[] availableMarkets;
    private Map<String, String> externalUrls;
    private String[] genres;
    private String href;
    @Id
    private String id;
    private Image[] images;
    private String name;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    private String restrictionReason;
    private ModelObjectType type;
    private String uri;

    public AlbumSimplified(se.michaelthelin.spotify.model_objects.specification.AlbumSimplified albumSimplified) {
        this.albumGroup = albumSimplified.getAlbumGroup();
        this.albumType = albumSimplified.getAlbumType();
        this.artists = Arrays.stream(albumSimplified.getArtists()).map(ArtistSimplified::new).toList();
        this.availableMarkets = albumSimplified.getAvailableMarkets();
        this.externalUrls = albumSimplified.getExternalUrls().getExternalUrls();
        this.href = albumSimplified.getHref();
        this.id = albumSimplified.getId();
        this.images = Arrays.stream(albumSimplified.getImages()).map(Image::new).toArray(Image[]::new);
        this.name = albumSimplified.getName();
        this.releaseDate = albumSimplified.getReleaseDate();
        this.releaseDatePrecision = albumSimplified.getReleaseDatePrecision();
        this.restrictionReason = albumSimplified.getRestrictions() == null ? null : albumSimplified.getRestrictions().getReason();
        this.type = albumSimplified.getType();
        this.uri = albumSimplified.getUri();
    }
}
