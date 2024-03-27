package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.ObjectType;
import de.wittenbude.exportify.models.embeds.ProductType;
import de.wittenbude.exportify.spotify.data.SpotifyObjectType;
import de.wittenbude.exportify.spotify.data.SpotifyProductType;

public class TypeConverter {


    public static ObjectType from(SpotifyObjectType spotifyObjectType) {
        for (ObjectType b : ObjectType.values()) {
            if (b.getType().equalsIgnoreCase(spotifyObjectType.getType())) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant in %s for value %s"
                .formatted(ObjectType.class.getTypeName(), spotifyObjectType.getType()));
    }


    public static ProductType from(SpotifyProductType spotifyProductType) {
        for (ProductType b : ProductType.values()) {
            if (b.getType().equalsIgnoreCase(spotifyProductType.getType())) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant in %s for value %s"
                .formatted(ObjectType.class.getTypeName(), spotifyProductType.getType()));
    }
}
