package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotifyArtist extends SpotifyArtistSimplified {

    @JsonProperty("followers")
    private SpotifyFollowers spotifyFollowers;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("images")
    private List<SpotifyImage> spotifyImages;

    @JsonProperty("popularity")
    private Integer popularity;

}
