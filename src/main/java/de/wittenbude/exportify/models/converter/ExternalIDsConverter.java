package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.ExternalIDs;
import de.wittenbude.exportify.spotify.data.SpotifyExternalIDs;

public class ExternalIDsConverter {

    public static ExternalIDs from(SpotifyExternalIDs spotifyExternalIDs) {
        return new ExternalIDs()
                .setEan(spotifyExternalIDs.getEan())
                .setUpc(spotifyExternalIDs.getUpc())
                .setIsrc(spotifyExternalIDs.getIsrc());
    }
}
