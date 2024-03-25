package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyTrack extends SpotifyTrackSimplified {

    @JsonProperty("album")
    private SpotifyAlbumSimplified album;

    @JsonProperty("album")
    private SpotifyArtist[] artists;

    @JsonProperty("external_ids")
    private SpotifyExternalIDs externalUrls;

    @JsonProperty("popularity")
    private Integer popularity;

}
