package de.wittenbude.expandify.models.db;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.pojos.TrackLink;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "tracks.infos")
public class TrackInfo {
    @DocumentReference(lazy = true)
    private List<ArtistInfo> artists;
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


    public TrackInfo(se.michaelthelin.spotify.model_objects.specification.TrackSimplified track) {
        this.artists = Arrays.stream(track.getArtists()).map(ArtistInfo::new).toList();
        this.discNumber = track.getDiscNumber();
        this.explicit = track.getIsExplicit();
        this.externalUrls = track.getExternalUrls().getExternalUrls();
        this.href = track.getHref();
        this.id = track.getId();
        this.isPlayable = track.getIsPlayable();
        this.linkedFrom = track.getLinkedFrom() == null ? null : new TrackLink(track.getLinkedFrom());
        this.name = track.getName();
        this.previewUrl = track.getPreviewUrl();
        this.trackNumber = track.getTrackNumber();
        this.type = track.getType();
        this.uri = track.getUri();
    }
}
