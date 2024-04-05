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
    private SpotifyPlaylistTrackItem track;
    //private JsonNode track;

}
