package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.user.api.PrivateSpotifyUser;
import de.wittenbude.exportify.user.api.PublicSpotifyUser;
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

    public static PrivateUserSchema from(PrivateSpotifyUser privateSpotifyUser, PublicSpotifyUser publicSpotifyUser) {
        return PrivateUserSchema
                .builder()
                .id(privateSpotifyUser.getId())
                .displayName(publicSpotifyUser.getDisplayName())
                .externalUrls(publicSpotifyUser.getExternalUrls())
                .followers(publicSpotifyUser.getFollowers())
                .href(publicSpotifyUser.getHref())
                .spotifyID(publicSpotifyUser.getSpotifyID())
                .images(ImageSchema.from(publicSpotifyUser.getImages()))
                .type(publicSpotifyUser.getSpotifyObjectType())
                .uri(publicSpotifyUser.getUri())
                .country(privateSpotifyUser.getCountry())
                .email(privateSpotifyUser.getEmail())
                .explicitContentFilterEnabled(privateSpotifyUser.getExplicitContentFilterEnabled())
                .explicitContentFilterLocked(privateSpotifyUser.getExplicitContentFilterLocked())
                .product(privateSpotifyUser.getSpotifyProductType())
                .build();
    }

}
