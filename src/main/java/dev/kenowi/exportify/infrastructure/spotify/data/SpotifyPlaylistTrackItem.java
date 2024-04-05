package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotifyTrack.class, name = "track"),
        @JsonSubTypes.Type(value = SpotifyEpisode.class, name = "episode")
})
public interface SpotifyPlaylistTrackItem {
    String getId();

    SpotifyObjectType getType();
}
