package de.wittenbude.exportify.dto;

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

    @JsonProperty("explicit_content_filter_enabled")
    private final Boolean explicitContentFilterEnabled;

    @JsonProperty("explicit_content_filter_locked")
    private final Boolean explicitContentFilterLocked;

    @JsonProperty("product")
    private final String product;


    public static PrivateUserSchema from(PrivateUser privateUser) {
        return PrivateUserSchema
                .builder()
                .id(privateUser.getId())
                .displayName(privateUser.getPublicUser().getDisplayName())
                .externalUrls(privateUser.getPublicUser().getExternalUrls())
                .followers(privateUser.getPublicUser().getFollowers().getTotal())
                .href(privateUser.getPublicUser().getHref())
                .spotifyID(privateUser.getPublicUser().getSpotifyID())
                .images(ImageSchema.fromAll(privateUser.getPublicUser().getImages()))
                .type(privateUser.getPublicUser().getObjectType().getType())
                .uri(privateUser.getPublicUser().getUri())
                .country(privateUser.getCountry())
                .email(privateUser.getEmail())
                .explicitContentFilterEnabled(privateUser.getExplicitContent().getFilterEnabled())
                .explicitContentFilterLocked(privateUser.getExplicitContent().getFilterLocked())
                .product(privateUser.getProduct().getType())
                .build();
    }
}
