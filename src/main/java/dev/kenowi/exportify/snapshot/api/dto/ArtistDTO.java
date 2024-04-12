package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ArtistDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version_timestamp")
    private Instant versionTimestamp;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("followers")
    private Integer followers;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("href")
    private String href;

    @JsonProperty("spotify_id")
    private String spotifyID;

    @JsonProperty("images")
    private List<ImageDTO> spotifyImages;

    @JsonProperty("name")
    private String name;

    @JsonProperty("popularity")
    private Integer popularity;

    @JsonProperty("type")
    private String spotifyObjectType;

    @JsonProperty("uri")
    private String uri;
}
