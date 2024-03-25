package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpotifyAlbumType {

    @JsonProperty("album")
    ALBUM("album"),

    @JsonProperty("compilation")
    COMPILATION("compilation"),

    @JsonProperty("single")
    SINGLE("single");


    private final String type;
}
