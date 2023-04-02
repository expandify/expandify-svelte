package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.spotifydata.helper.TrackLink;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "tracks")
public class Track {
    @DocumentReference(lazy = true)
    private AlbumSimplified album;
    @DocumentReference(lazy = true)
    private List<ArtistSimplified> artists;
    private CountryCode[] availableMarkets;
    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;
    private Map<String, String> externalIds;
    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private Boolean isPlayable;
    private TrackLink linkedFrom;
    private String restrictionReason;
    private String name;
    private Integer popularity;
    private String previewUrl;
    private Integer trackNumber;
    private ModelObjectType type;
    private String uri;


    public Track(se.michaelthelin.spotify.model_objects.specification.Track track) {
        this.album = new AlbumSimplified(track.getAlbum());
        this.artists = Arrays.stream(track.getArtists()).map(ArtistSimplified::new).toList();
        this.discNumber = track.getDiscNumber();
        this.explicit = track.getIsExplicit();
        this.externalIds = track.getExternalIds().getExternalIds();
        this.externalUrls = track.getExternalUrls().getExternalUrls();
        this.href = track.getHref();
        this.id = track.getId();
        this.isPlayable = track.getIsPlayable();
        this.linkedFrom = new TrackLink(track.getLinkedFrom());
        this.restrictionReason = track.getRestrictions().getReason();
        this.name = track.getName();
        this.popularity = track.getPopularity();
        this.previewUrl = track.getPreviewUrl();
        this.trackNumber = track.getTrackNumber();
        this.type = track.getType();
        this.uri = track.getUri();
    }
}
