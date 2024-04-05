package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyPublicUser {

    @JsonProperty("external_urls")
    protected Map<String, String> externalUrls;

    @JsonProperty("followers")
    protected SpotifyFollowers spotifyFollowers;

    @JsonProperty("href")
    protected String href;

    @JsonProperty("id")
    protected String id;

    @JsonProperty("type")
    protected SpotifyObjectType type;

    @JsonProperty("images")
    protected List<SpotifyImage> spotifyImages;

    @JsonProperty("uri")
    protected String uri;

    @JsonProperty("display_name")
    protected String displayName;

}
