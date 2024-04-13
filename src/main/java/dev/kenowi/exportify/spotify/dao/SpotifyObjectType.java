package dev.kenowi.exportify.spotify.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SpotifyObjectType {

    @JsonProperty("album")
    ALBUM,

    @JsonProperty("artist")
    ARTIST,

    @JsonProperty("audio_features")
    AUDIO_FEATURES,

    @JsonProperty("episode")
    EPISODE,

    @JsonProperty("genre")
    GENRE,

    @JsonProperty("playlist")
    PLAYLIST,

    @JsonProperty("show")
    SHOW,

    @JsonProperty("track")
    TRACK,

    @JsonProperty("user")
    USER

}
