package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(onMethod_={@JsonValue})
@AllArgsConstructor
public enum ProductType {
    BASIC_DESKTOP("basic-desktop"),
    DAYPASS("daypass"),
    FREE("free"),
    OPEN("open"),
    PREMIUM("premium");

    private final String type;
}
