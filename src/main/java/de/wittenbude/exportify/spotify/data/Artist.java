package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Artist {

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("followers")
    private Followers followers;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private Image[] images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("popularity")
    private Integer popularity;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

}
