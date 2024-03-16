package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivateUser extends User {
    @JsonProperty("country")
    private CountryCode country;

    @JsonProperty("email")
    private String email;

    @JsonProperty("explicit_content")
    private ExplicitContent explicitContent;

    @JsonProperty("product")
    private ProductType product;
}
