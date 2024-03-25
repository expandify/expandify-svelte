package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.ObjectType;
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
        return Artist
                .builder()
                .id(null)
                .externalUrls(externalUrls)
                .followers(spotifyFollowers.convert())
                .genres(Arrays.asList(genres))
                .href(href)
                .spotifyID(id)
                .images(SpotifyImage.convertAll(spotifyImages))
                .name(name)
                .popularity(popularity)
                .type(ObjectType.valueOf(type.getType()))
                .uri(uri)
                .build();
    }

}
