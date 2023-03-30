package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.TrackLink;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "tracksSimplified")
public class TrackSimplified {
    @DocumentReference(lazy = true)
    private ArtistSimplified[] artistSimplified;
    private CountryCode[] availableMarkets;
    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;
    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private Boolean isPlayable;
    private TrackLink linkedFrom;
    private String name;
    private String previewUrl;
    private Integer trackNumber;
    private ModelObjectType type;
    private String uri;


    public TrackSimplified(se.michaelthelin.spotify.model_objects.specification.TrackSimplified track) {
        this.artistSimplified = Arrays.stream(track.getArtists()).map(ArtistSimplified::new).toArray(ArtistSimplified[]::new);
        this.discNumber = track.getDiscNumber();
        this.explicit = track.getIsExplicit();
        this.externalUrls = track.getExternalUrls().getExternalUrls();
        this.href = track.getHref();
        this.id = track.getId();
        this.isPlayable = track.getIsPlayable();
        this.linkedFrom = new TrackLink(track.getLinkedFrom());
        this.name = track.getName();
        this.previewUrl = track.getPreviewUrl();
        this.trackNumber = track.getTrackNumber();
        this.type = track.getType();
        this.uri = track.getUri();
    }
}
