package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpotifyPlaylistTrack {

    @JsonProperty("added_at")
    private Date addedAt;

    @JsonProperty("added_by")
    private SpotifyPublicUser addedBy;

    @JsonProperty("is_local")
    private Boolean isLocal;

    @JsonProperty("track")
    private SpotifyIdProjection track;

    // TODO: Is this better? Does a playlistTrack contain all information? How to handle Events?
    //private SpotifyPlaylistTrackItem track;


}
