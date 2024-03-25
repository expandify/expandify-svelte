package de.wittenbude.exportify.http.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.PrivateUser;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PrivateUserSchema extends PublicUserSchema {


    @JsonProperty("country")
    private final CountryCode country;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("explicit_content")
    private final ExplicitContentSchema explicitContent;

    @JsonProperty("product")
    private final String product;


    public static PrivateUserSchema from(PrivateUser privateUser) {
        return PrivateUserSchema
                .builder()
                .id(privateUser.getId())
                .displayName(privateUser.getDisplayName())
                .externalUrls(privateUser.getExternalUrls())
                .followers(FollowersSchema.from(privateUser.getFollowers()))
                .href(privateUser.getHref())
                .spotifyID(privateUser.getSpotifyID())
                .images(ImageSchema.fromAll(privateUser.getImages()))
                .type(privateUser.getType().getType())
                .uri(privateUser.getUri())
                .country(privateUser.getCountry())
                .email(privateUser.getEmail())
                .explicitContent(ExplicitContentSchema.from(privateUser.getExplicitContent()))
                .product(privateUser.getProduct().getType())
                .build();
    }
}
