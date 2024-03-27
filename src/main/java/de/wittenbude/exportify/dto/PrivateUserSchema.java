package de.wittenbude.exportify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
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

}
