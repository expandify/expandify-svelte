package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyArtist extends SpotifyArtistSimplified {

    @JsonProperty("followers")
    private SpotifyFollowers spotifyFollowers;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("images")
    private SpotifyImage[] spotifyImages;

    @JsonProperty("popularity")
    private Integer popularity;

}
