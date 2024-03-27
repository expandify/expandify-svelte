package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageSchema {
    @JsonProperty("height")
    private final Integer height;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("width")
    private final Integer width;

}
