package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifySavedTrack {

    @JsonProperty("added_at")
    private String addedAt;

    @JsonProperty("track")
    private SpotifyTrack track;
}
