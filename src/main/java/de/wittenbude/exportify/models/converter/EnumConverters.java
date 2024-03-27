package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.ReleaseDatePrecision;
import de.wittenbude.exportify.spotify.data.SpotifyReleaseDatePrecision;

public class EnumConverters {

    public static ReleaseDatePrecision from(SpotifyReleaseDatePrecision spotifyReleaseDatePrecision) {
        return switch (spotifyReleaseDatePrecision) {
            case DAY -> ReleaseDatePrecision.DAY;
            case MONTH -> ReleaseDatePrecision.MONTH;
            case YEAR -> ReleaseDatePrecision.YEAR;
        };
    }
}
