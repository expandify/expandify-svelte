package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyFollowers {
    @JsonProperty("href")
    private String href;

    @JsonProperty("total")
    private Integer total;

}
