package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class TrackDTO {


    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version_timestamp")
    private Instant versionTimestamp;

    @JsonProperty("disc_number")
    private Integer discNumber;

    @JsonProperty("duration_ms")
    private Integer durationMs;

    @JsonProperty("explicit")
    private Boolean explicit;

    @JsonProperty("href")
    private String href;

    @JsonProperty("spotify_id")
    private String spotifyID;

    @JsonProperty("is_playable")
    private Boolean isPlayable;

    @JsonProperty("restrictions")
    private String restrictions;

    @JsonProperty("name")
    private String name;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("track_number")
    private Integer trackNumber;

    @JsonProperty("spotify_object_type")
    private String spotifyObjectType;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("is_local")
    private Boolean isLocal;

    @JsonProperty("external_ids")
    private ExternalIDsDTO externalIDs;

    @JsonProperty("popularity")
    private Integer popularity;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("available_markets")
    private List<CountryCode> availableMarkets;

    @JsonProperty("spotify_album_id")
    private String spotifyAlbumID;

    @JsonProperty("spotify_artist_ids")
    private List<String> spotifyArtistIDs;

}