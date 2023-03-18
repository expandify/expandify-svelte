package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.*;
import se.michaelthelin.spotify.model_objects.specification.Followers;

@Data
@NoArgsConstructor
public class SpotifyFollowers {
    private String href;
    private Integer total;

    public SpotifyFollowers(Followers followers) {
        this.href = followers.getHref();
        this.total = followers.getTotal();
    }
}
