package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyExternalIDs {

    @JsonProperty("isrc")
    private String isrc;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("upc")
    private String upc;

}
