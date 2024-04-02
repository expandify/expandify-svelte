package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyTrackSimplified {

    @JsonProperty("artists")
    private List<SpotifyArtistSimplified> artistsSimplified;

    @JsonProperty("available_markets")
    private List<CountryCode> availableMarkets;

    @JsonProperty("disc_number")
    private Integer discNumber;

    @JsonProperty("duration_ms")
    private Integer durationMs;

    @JsonProperty("explicit")
    private Boolean explicit;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_playable")
    private Boolean isPlayable;

    @JsonProperty("linked_from")
    private SpotifyTrackLink linkedFrom;

    @JsonProperty("restriction")
    private SpotifyRestrictions restrictions;

    @JsonProperty("name")
    private String name;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("track_number")
    private Integer trackNumber;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("is_local")
    private Boolean isLocal;

}
