package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.PrivateUser;
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

    public PrivateUser convertPrivate() {
        return new PrivateUser()
                .setPublicUser(this.convert())
                .setCountry(country)
                .setEmail(email)
                .setProduct(product.convert())
                .setExplicitContent(spotifyExplicitContent.convert());
    }
}
