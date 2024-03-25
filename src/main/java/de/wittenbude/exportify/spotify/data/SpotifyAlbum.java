package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyAlbum extends SpotifyAlbumSimplified {

    @JsonProperty("tracks")
    private SpotifyPage<SpotifyTrackSimplified> tracks;

    @JsonProperty("copyrights")
    private SpotifyCopyright[] copyrights;

    @JsonProperty("externalIDs")
    private SpotifyExternalIDs externalIDs;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("label")
    private String label;

    @JsonProperty("popularity")
    private Integer popularity;
}
