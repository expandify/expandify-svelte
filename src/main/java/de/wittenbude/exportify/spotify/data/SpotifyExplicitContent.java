package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyExplicitContent {
    @JsonProperty("filter_enabled")
    private Boolean filterEnabled;

    @JsonProperty("filter_locked")
    private Boolean filterLocked;

}
