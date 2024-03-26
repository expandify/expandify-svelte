package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.ObjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpotifyObjectType {

    @JsonProperty("album")
    ALBUM("album"),

    @JsonProperty("artist")
    ARTIST("artist"),

    @JsonProperty("audio_features")
    AUDIO_FEATURES("audio_features"),

    @JsonProperty("episode")
    EPISODE("episode"),

    @JsonProperty("genre")
    GENRE("genre"),

    @JsonProperty("playlist")
    PLAYLIST("playlist"),

    @JsonProperty("show")
    SHOW("show"),

    @JsonProperty("track")
    TRACK("track"),

    @JsonProperty("user")
    USER("user");

    private final String type;

    public ObjectType convert() {
        for (ObjectType b : ObjectType.values()) {
            if (b.getType().equalsIgnoreCase(type)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant in %s for value %s"
                .formatted(ObjectType.class.getTypeName(), type));
    }

}
