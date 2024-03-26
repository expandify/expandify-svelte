package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.PublicUser;
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
    protected SpotifyObjectType type;

    @JsonProperty("uri")
    protected String uri;


    public PublicUser convert() {
        return new PublicUser()
                .setDisplayName(displayName)
                .setExternalUrls(externalUrls)
                .setFollowers(spotifyFollowers.convert())
                .setHref(href)
                .setSpotifyID(id)
                .setImages(SpotifyImage.convertAll(spotifyImages))
                .setObjectType(type.convert())
                .setUri(uri);
    }

}
