package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyShow extends SpotifyShowSimplified {

    @JsonProperty("episodes")
    private SpotifyPage<SpotifyEpisode> episodes;
}
