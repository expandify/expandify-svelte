package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ArtistSchema {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("version_timestamp")
    private final Instant versionTimestamp;

    @JsonProperty("external_urls")
    private final Map<String, String> externalUrls;

    @JsonProperty("followers")
    private final Integer followers;

    @JsonProperty("genres")
    private final List<String> genres;

    @JsonProperty("href")
    private final String href;

    @JsonProperty("spotify_id")
    private final String spotifyID;

    @JsonProperty("images")
    private final List<ImageSchema> spotifyImages;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("popularity")
    private final Integer popularity;

    @JsonProperty("type")
    private final String spotifyObjectType;

    @JsonProperty("uri")
    private final String uri;


}
