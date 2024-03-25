package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter()
@AllArgsConstructor
public enum SpotifyProductType {

    @JsonProperty("basic-desktop")
    BASIC_DESKTOP("basic-desktop"),

    @JsonProperty("daypass")
    DAYPASS("daypass"),

    @JsonProperty("free")
    FREE("free"),

    @JsonProperty("open")
    OPEN("open"),

    @JsonProperty("premium")
    PREMIUM("premium");


    private final String type;
}
