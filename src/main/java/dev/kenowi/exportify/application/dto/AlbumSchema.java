package dev.kenowi.exportify.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AlbumSchema {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version_timestamp")
    private Instant versionTimestamp;

    @JsonProperty("album_type")
    private String albumType;

    @JsonProperty("total_tracks")
    private Integer totalTracks;

    @JsonProperty("href")
    private String href;

    @JsonProperty("spotify_id")
    private String spotifyID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;

    @JsonProperty("restrictions")
    private String restrictions;

    @JsonProperty("spotify_object_type")
    private String spotifyObjectType;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("external_ids")
    private ExternalIDsSchema externalIDs;

    @JsonProperty("label")
    private String label;

    @JsonProperty("popularity")
    private Integer popularity;

    @JsonProperty("copyrights")
    private List<CopyrightSchema> copyrights;

    @JsonProperty("images")
    private List<ImageSchema> images;

    @JsonProperty("available_markets")
    private List<CountryCode> availableMarkets;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("spotify_artist_ids")
    private List<String> spotifyArtistIDs;

    @JsonProperty("spotify_track_ids")
    private List<String> spotifyTrackIDs;

    @Getter
    @Builder
    public static class CopyrightSchema {

        @JsonProperty("text")
        private String text;

        @JsonProperty("type")
        private String type;
    }

}


