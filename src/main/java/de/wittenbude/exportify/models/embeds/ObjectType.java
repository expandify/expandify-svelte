package de.wittenbude.exportify.models.embeds;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ObjectType {

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
