package de.wittenbude.exportify.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PublicUserSchema {
    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("external_urls")
    protected Map<String, String> externalUrls;

    @JsonProperty("followers")
    protected Integer followers;

    @JsonProperty("href")
    protected String href;

    @JsonProperty("spotify_id")
    protected String spotifyID;

    @JsonProperty("images")
    protected List<ImageSchema> images;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("uri")
    protected String uri;

    @JsonProperty("id")
    protected UUID id;


}