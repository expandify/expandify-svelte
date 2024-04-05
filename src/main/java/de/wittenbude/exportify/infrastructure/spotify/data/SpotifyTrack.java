package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotifyTrack extends SpotifyTrackSimplified implements SpotifyPlaylistTrackItem {

    @JsonProperty("album")
    private SpotifyAlbumSimplified album;

    @JsonProperty("artists")
    private List<SpotifyArtist> artists;

    @JsonProperty("external_ids")
    private SpotifyExternalIDs externalIDs;

    @JsonProperty("popularity")
    private Integer popularity;

}
