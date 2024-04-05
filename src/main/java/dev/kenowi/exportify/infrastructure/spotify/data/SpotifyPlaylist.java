package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotifyPlaylist extends SpotifyPlaylistSimplified {

    @JsonProperty("followers")
    private SpotifyFollowers followers;

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("tracks")
    private SpotifyPage<SpotifyPlaylistTrack> tracks;
}
