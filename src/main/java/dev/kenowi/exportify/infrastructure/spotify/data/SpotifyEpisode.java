package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyEpisode extends SpotifyEpisodeSimplified implements SpotifyPlaylistTrackItem {

    @JsonProperty("show")
    private SpotifyShowSimplified show;
}
