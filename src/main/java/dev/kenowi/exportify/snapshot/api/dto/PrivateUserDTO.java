package dev.kenowi.exportify.snapshot.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PrivateUserDTO {

    @JsonProperty("id")
    protected UUID id;

    @JsonProperty("spotify_id")
    protected String spotifyID;

    @JsonProperty("country")
    private CountryCode country;

    @JsonProperty("email")
    private String email;

    @JsonProperty("explicit_content_filter_enabled")
    private Boolean explicitContentFilterEnabled;

    @JsonProperty("explicit_content_filter_locked")
    private Boolean explicitContentFilterLocked;

    @JsonProperty("product")
    private String product;

}
