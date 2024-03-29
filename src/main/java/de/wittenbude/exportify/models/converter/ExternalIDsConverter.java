package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.ExternalIDsSchema;
import de.wittenbude.exportify.models.embeds.ExternalIDs;
import de.wittenbude.exportify.spotify.data.SpotifyExternalIDs;

public class ExternalIDsConverter {

    public static ExternalIDs from(SpotifyExternalIDs spotifyExternalIDs) {
        if (spotifyExternalIDs == null) {
            return null;
        }
        return new ExternalIDs()
                .setEan(spotifyExternalIDs.getEan())
                .setUpc(spotifyExternalIDs.getUpc())
                .setIsrc(spotifyExternalIDs.getIsrc());
    }

    public static ExternalIDsSchema toDTO(ExternalIDs externalIDs) {
        if (externalIDs == null) {
            return null;
        }
        return ExternalIDsSchema
                .builder()
                .ean(externalIDs.getEan())
                .upc(externalIDs.getUpc())
                .isrc(externalIDs.getIsrc())
                .build();
    }
}
