package dev.kenowi.exportify.spotify.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotifyAlbum extends SpotifyAlbumSimplified {

    @JsonProperty("tracks")
    private SpotifyPage<SpotifyTrackSimplified> tracks;

    @JsonProperty("copyrights")
    private List<SpotifyCopyright> copyrights;

    @JsonProperty("externalIDs")
    private SpotifyExternalIDs externalIDs;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("label")
    private String label;

    @JsonProperty("popularity")
    private Integer popularity;

}
