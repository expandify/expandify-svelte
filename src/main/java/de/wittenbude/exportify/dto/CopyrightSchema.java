package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CopyrightSchema {

    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private String type;
}