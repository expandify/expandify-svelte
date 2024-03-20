package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.PublicUser;
import de.wittenbude.exportify.models.SpotifyObjectType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SpotifyPublicUser {
    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("external_urls")
    protected Map<String, String> externalUrls;

    @JsonProperty("followers")
    protected SpotifyFollowers spotifyFollowers;

    @JsonProperty("href")
    protected String href;

    @JsonProperty("id")
    protected String id;

    @JsonProperty("images")
    protected SpotifyImage[] spotifyImages;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("uri")
    protected String uri;


    public PublicUser convert() {
        return PublicUser
                .builder()
                .displayName(displayName)
                .externalUrls(externalUrls)
                .followers(spotifyFollowers.convert())
                .href(href)
                .spotifyID(id)
                .images(SpotifyImage.convertAll(spotifyImages))
                .type(SpotifyObjectType.valueOf(type.toUpperCase()))
                .uri(uri)
                .build();
    }

}
