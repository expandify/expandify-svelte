package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SpotifyShowSimplified {

    @JsonProperty("availableMarkets")
    private List<CountryCode> availableMarkets;

    @JsonProperty("copyrights")
    private List<SpotifyCopyright> copyrights;

    @JsonProperty("description")
    private String description;

    @JsonProperty("html_description")
    private String htmlDescription;

    @JsonProperty("explicit")
    private Boolean explicit;

    @JsonProperty("externalUrls")
    private Map<String, String> externalUrls;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("isExternallyHosted")
    private Boolean isExternallyHosted;

    @JsonProperty("languages")
    private List<String> languages;

    @JsonProperty("mediaType")
    private String mediaType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("type")
    private SpotifyObjectType type;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("total_episodes")
    private Integer totalEpisodes;
}
