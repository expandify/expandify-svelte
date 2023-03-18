package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifyTrackLink;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "tracksSimplified")
public class SpotifyTrackSimplified {
    @DocumentReference(lazy = true)
    private SpotifyArtistSimplified[] artistSimplified;
    private CountryCode[] availableMarkets;
    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;
    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private Boolean isPlayable;
    private SpotifyTrackLink linkedFrom;
    private String name;
    private String previewUrl;
    private Integer trackNumber;
    private ModelObjectType type;
    private String uri;


    public SpotifyTrackSimplified(TrackSimplified track) {
        this.artistSimplified = Arrays.stream(track.getArtists()).map(SpotifyArtistSimplified::new).toArray(SpotifyArtistSimplified[]::new);
        this.discNumber = track.getDiscNumber();
        this.explicit = track.getIsExplicit();
        this.externalUrls = track.getExternalUrls().getExternalUrls();
        this.href = track.getHref();
        this.id = track.getId();
        this.isPlayable = track.getIsPlayable();
        this.linkedFrom = new SpotifyTrackLink(track.getLinkedFrom());
        this.name = track.getName();
        this.previewUrl = track.getPreviewUrl();
        this.trackNumber = track.getTrackNumber();
        this.type = track.getType();
        this.uri = track.getUri();
    }
}
