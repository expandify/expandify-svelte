package de.wittenbude.exportify.models;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PrivateUser extends PublicUser {

    private CountryCode country;
    private String email;
    private ExplicitContent explicitContent;
    private ProductType product;

}

