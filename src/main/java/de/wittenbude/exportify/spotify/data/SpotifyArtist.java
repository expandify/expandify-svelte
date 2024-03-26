package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Artist;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class SpotifyArtist extends SpotifyArtistSimplified {

    @JsonProperty("followers")
    private SpotifyFollowers spotifyFollowers;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("images")
    private SpotifyImage[] spotifyImages;

    @JsonProperty("popularity")
    private Integer popularity;

    public Artist convert() {
        return new Artist()
                .setId(null)
                .setExternalUrls(externalUrls)
                .setFollowers(spotifyFollowers.convert())
                .setGenres(Arrays.asList(genres))
                .setHref(href)
                .setSpotifyID(id)
                .setImages(SpotifyImage.convertAll(spotifyImages))
                .setName(name)
                .setPopularity(popularity)
                .setObjectType(type.convert())
                .setUri(uri);
    }

}
