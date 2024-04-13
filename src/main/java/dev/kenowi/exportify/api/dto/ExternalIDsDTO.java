package dev.kenowi.exportify.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalIDsDTO {

    @JsonProperty("isrc")
    private String isrc;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("upc")
    private String upc;
}
