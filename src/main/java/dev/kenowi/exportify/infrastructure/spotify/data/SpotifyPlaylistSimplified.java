package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyPlaylistSimplified {

    @JsonProperty("collaborative")
    private Boolean collaborative;

    @JsonProperty("description")
    private String description;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private SpotifyPublicUser owner;

    @JsonProperty("public")
    private Boolean publicAccess;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    @JsonProperty("tracks")
    private Tracks simplifiedTracks;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

    @Getter
    @Setter
    public static class Tracks {

        @JsonProperty("href")
        private String href;

        @JsonProperty("total")
        private Integer total;
    }
}
