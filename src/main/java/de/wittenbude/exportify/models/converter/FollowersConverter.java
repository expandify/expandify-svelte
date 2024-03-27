package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.Followers;
import de.wittenbude.exportify.spotify.data.SpotifyFollowers;

public class FollowersConverter {


    public static Followers from(SpotifyFollowers spotifyFollowers) {
        return new Followers()
                .setTotal(spotifyFollowers.getTotal())
                .setHref(spotifyFollowers.getHref());
    }
}
