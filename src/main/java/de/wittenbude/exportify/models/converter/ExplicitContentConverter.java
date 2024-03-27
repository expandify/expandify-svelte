package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.ExplicitContent;
import de.wittenbude.exportify.spotify.data.SpotifyExplicitContent;

public class ExplicitContentConverter {


    public static ExplicitContent from(SpotifyExplicitContent spotifyExplicitContent) {
        return new ExplicitContent()
                .setFilterEnabled(spotifyExplicitContent.getFilterEnabled())
                .setFilterLocked(spotifyExplicitContent.getFilterLocked());
    }
}
