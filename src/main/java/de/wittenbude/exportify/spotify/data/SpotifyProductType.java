package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.embeds.ObjectType;
import de.wittenbude.exportify.models.embeds.ProductType;
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

    public ProductType convert() {
        for (ProductType b : ProductType.values()) {
            if (b.getType().equalsIgnoreCase(type)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant in %s for value %s"
                .formatted(ObjectType.class.getTypeName(), type));
    }
}
