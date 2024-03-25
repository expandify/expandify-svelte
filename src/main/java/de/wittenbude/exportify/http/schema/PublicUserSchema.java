package de.wittenbude.exportify.http.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.PublicUser;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@SuperBuilder
public class PublicUserSchema {
    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("display_name")
    protected final String displayName;

    @JsonProperty("external_urls")
    protected final Map<String, String> externalUrls;

    @JsonProperty("followers")
    protected final FollowersSchema followers;

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

    public PublicUserSchema from(PublicUser publicUser) {
        return PublicUserSchema
                .builder()
                .id(publicUser.getId())
                .displayName(publicUser.getDisplayName())
                .externalUrls(publicUser.getExternalUrls())
                .followers(FollowersSchema.from(publicUser.getFollowers()))
                .href(publicUser.getHref())
                .spotifyID(publicUser.getSpotifyID())
                .images(ImageSchema.fromAll(publicUser.getImages()))
                .type(publicUser.getType().getType())
                .uri(publicUser.getUri())
                .build();
    }

}
