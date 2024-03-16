package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class User {
    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("followers")
    private Followers followers;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<Image> images;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;




}
