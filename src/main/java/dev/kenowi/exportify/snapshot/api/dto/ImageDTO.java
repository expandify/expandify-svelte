package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    @JsonProperty("height")
    private Integer height;

    @JsonProperty("url")
    private String url;

    @JsonProperty("width")
    private Integer width;
}
