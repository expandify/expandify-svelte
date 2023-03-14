package de.wittenbude.expandify.models.spotifydata;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.miscellaneous.Restrictions;
import se.michaelthelin.spotify.model_objects.specification.*;

@Data
@NoArgsConstructor
@Document(collection = "tracks")
public class SpotifyTrack {
    private AlbumSimplified album;
    private ArtistSimplified[] artists;
    private CountryCode[] availableMarkets;
    private Integer discNumber;
    private Integer durationMs;
    private Boolean explicit;
    private ExternalId externalIds;
    private ExternalUrl externalUrls;
    private String href;
    @Id
    private String id;
    private Boolean isPlayable;
    private TrackLink linkedFrom;
    private Restrictions restrictions;
    private String name;
    private Integer popularity;
    private String previewUrl;
    private Integer trackNumber;
    private ModelObjectType type;
    private String uri;
}
