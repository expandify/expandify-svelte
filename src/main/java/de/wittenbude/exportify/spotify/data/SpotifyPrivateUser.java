package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.models.ProductType;
import de.wittenbude.exportify.models.ObjectType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyPrivateUser extends SpotifyPublicUser {
    @JsonProperty("country")
    private CountryCode country;

    @JsonProperty("email")
    private String email;

    @JsonProperty("explicit_content")
    private SpotifyExplicitContent spotifyExplicitContent;

    @JsonProperty("product")
    private SpotifyProductType product;

    public PrivateUser convert() {
        return PrivateUser
                .builder()
                .displayName(displayName)
                .externalUrls(externalUrls)
                .followers(spotifyFollowers.convert())
                .href(href)
                .spotifyID(id)
                .images(SpotifyImage.convertAll(spotifyImages))
                .type(ObjectType.valueOf(type.getType()))
                .uri(uri)
                .country(country)
                .email(email)
                .product(ProductType.valueOf(product.getType()))
                .explicitContent(spotifyExplicitContent.convert())
                .build();
    }
}
