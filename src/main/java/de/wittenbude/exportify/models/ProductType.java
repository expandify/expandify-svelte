package de.wittenbude.exportify.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter()
@AllArgsConstructor
public enum ProductType {
    BASIC_DESKTOP("basic-desktop"),
    DAYPASS("daypass"),
    FREE("free"),
    OPEN("open"),
    PREMIUM("premium");

    private final String type;
}
