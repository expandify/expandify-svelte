package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpotifyReleaseDatePrecision {

    @JsonProperty("day")
    DAY("day"),

    @JsonProperty("month")
    MONTH("month"),

    @JsonProperty("year")
    YEAR("year");


    private final String precision;
}
