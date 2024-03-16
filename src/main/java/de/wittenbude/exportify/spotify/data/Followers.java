package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Followers {
    @JsonProperty("href")
    private String href;

    @JsonProperty("total")
    private Integer total;
}
