package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.user.api.PublicSpotifyUser;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Getter
@SuperBuilder
public class PublicUserSchema {
    @JsonProperty("display_name")
    protected final String displayName;
    @JsonProperty("external_urls")
    protected final Map<String, String> externalUrls;
    @JsonProperty("followers")
    protected final Integer followers;
    @JsonProperty("href")
    protected final String href;
    @JsonProperty("spotify_id")
    protected final String spotifyID;
    @JsonProperty("images")
    protected final List<ImageSchema> images;
    @JsonProperty("type")
    protected final String type;
    @JsonProperty("uri")
    protected final String uri;
    @JsonProperty("id")
    private final UUID id;

    public static PublicUserSchema from(PublicSpotifyUser publicSpotifyUser) {
        return PublicUserSchema
                .builder()
                .id(publicSpotifyUser.getId())
                .displayName(publicSpotifyUser.getDisplayName())
                .externalUrls(publicSpotifyUser.getExternalUrls())
                .followers(publicSpotifyUser.getFollowers())
                .href(publicSpotifyUser.getHref())
                .spotifyID(publicSpotifyUser.getSpotifyID())
                .images(ImageSchema.from(publicSpotifyUser.getImages()))
                .type(publicSpotifyUser.getSpotifyObjectType())
                .uri(publicSpotifyUser.getUri())
                .build();
    }

    @Getter
    @Builder
    public static class ImageSchema {
        @JsonProperty("height")
        private final Integer height;

        @JsonProperty("url")
        private final String url;

        @JsonProperty("width")
        private final Integer width;


        public static List<ImageSchema> from(Collection<PublicSpotifyUser.Image> images) {
            List<ImageSchema> imageSchemas = new ArrayList<>();
            for (PublicSpotifyUser.Image image : images) {
                imageSchemas.add(ImageSchema.from(image));
            }
            return imageSchemas;
        }

        public static ImageSchema from(PublicSpotifyUser.Image image) {
            return ImageSchema
                    .builder()
                    .url(image.getUrl())
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();
        }
    }
}
