package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SpotifyReleaseDatePrecision {

    @JsonProperty("day")
    DAY,

    @JsonProperty("month")
    MONTH,

    @JsonProperty("year")
    YEAR
}
