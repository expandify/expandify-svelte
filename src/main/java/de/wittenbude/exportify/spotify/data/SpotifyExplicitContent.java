package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.ExplicitContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyExplicitContent {
    @JsonProperty("filter_enabled")
    private Boolean filterEnabled;

    @JsonProperty("filter_locked")
    private Boolean filterLocked;

    public ExplicitContent convert() {
        return new ExplicitContent()
                .setFilterEnabled(filterEnabled)
                .setFilterLocked(filterLocked);
    }
}
