package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyEpisodeSimplified {


    @JsonProperty("audio_preview_url")
    private String audioPreviewUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("html_description")
    private String htmlDescription;

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

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("is_externally_hosted")
    private Boolean isExternallyHosted;

    @JsonProperty("is_playable")
    private Boolean isPlayable;

    @JsonProperty("languages")
    private List<String> languages;

    @JsonProperty("name")
    private String name;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("release_date_precision")
    private SpotifyReleaseDatePrecision releaseDatePrecision;

    @JsonProperty("resume_point")
    private ResumePoint resumePoint;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("restrictions")
    private SpotifyRestrictions restrictions;

    @Getter
    @Setter
    public static class ResumePoint {

        @JsonProperty("fully_played")
        private Boolean fullyPlayed;

        @JsonProperty("resume_position_ms")
        private Integer resumePositionMs;
    }
}
