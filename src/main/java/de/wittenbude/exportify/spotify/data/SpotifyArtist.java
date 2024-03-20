package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.SpotifyObjectType;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class SpotifyArtist {

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("followers")
    private SpotifyFollowers spotifyFollowers;

    @JsonProperty("genres")
    private String[] genres;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private SpotifyImage[] spotifyImages;

    @JsonProperty("name")
    private String name;

    @JsonProperty("popularity")
    private Integer popularity;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uri")
    private String uri;


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
                .type(SpotifyObjectType.valueOf(type.toUpperCase()))
                .uri(uri)
                .build();
    }

}
