package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyCopyright {
    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private String type;

}
