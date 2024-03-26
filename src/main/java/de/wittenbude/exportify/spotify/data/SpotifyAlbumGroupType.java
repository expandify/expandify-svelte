package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpotifyAlbumGroupType {

    @JsonProperty("album")
    ALBUM("album"),

    @JsonProperty("single")
    SINGLE("single"),

    @JsonProperty("compilation")
    COMPILATION("compilation"),

    @JsonProperty("appears_on")
    APPEARS_ON("appears_on");


    public final String type;
}
