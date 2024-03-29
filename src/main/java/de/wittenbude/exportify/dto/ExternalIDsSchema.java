package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExternalIDsSchema {

    @JsonProperty("isrc")
    private final String isrc;

    @JsonProperty("ean")
    private final String ean;

    @JsonProperty("upc")
    private final String upc;


}
