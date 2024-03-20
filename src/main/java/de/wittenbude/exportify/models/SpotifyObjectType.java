package de.wittenbude.exportify.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
