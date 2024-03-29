package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.user.api.PublicSpotifyUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static de.wittenbude.exportify.utils.Converters.ifNotNull;

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

    public PublicSpotifyUser convertPublic() {
        return new PublicSpotifyUser()
                .setDisplayName(this.getDisplayName())
                .setExternalUrls(this.getExternalUrls())
                .setFollowers(this.getSpotifyFollowers().getTotal())
                .setHref(this.getHref())
                .setSpotifyID(this.getId())
                .setImages(ifNotNull(this.getSpotifyImages(), SpotifyImage::convert))
                .setSpotifyObjectType(this.getType().getType())
                .setUri(this.getUri());
    }

    @Getter
    @Setter
    public static class SpotifyImage {
        @JsonProperty("height")
        private Integer height;

        @JsonProperty("url")
        private String url;

        @JsonProperty("width")
        private Integer width;

        public static List<PublicSpotifyUser.Image> convert(SpotifyImage[] spotifyImages) {
            return Arrays
                    .stream(spotifyImages)
                    .map(SpotifyImage::convert)
                    .toList();
        }

        public PublicSpotifyUser.Image convert() {
            return new PublicSpotifyUser.Image()
                    .setUrl(this.getUrl())
                    .setWidth(this.getWidth())
                    .setHeight(this.getHeight());
        }
    }
}
