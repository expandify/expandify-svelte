package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(onMethod_={@JsonValue})
@AllArgsConstructor
public enum SpotifyObjectType {

        ALBUM("album"),
        ARTIST("artist"),
        AUDIO_FEATURES("audio_features"),
        EPISODE("episode"),
        GENRE("genre"),
        PLAYLIST("playlist"),
        SHOW("show"),
        TRACK("track"),
        USER("user");

        private final String type;
}
