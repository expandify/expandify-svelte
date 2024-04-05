package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SpotifyArtistSimplified {

    @JsonProperty("externalUrls")
    protected Map<String, String> externalUrls;

    @JsonProperty("href")
    protected String href;

    @JsonProperty("id")
    protected String id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("type")
    protected SpotifyObjectType type;

    @JsonProperty("uri")
    protected String uri;

}
