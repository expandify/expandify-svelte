package de.wittenbude.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
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


    @Getter
    public enum SpotifyProductType {

        @JsonProperty("basic-desktop")
        BASIC_DESKTOP,

        @JsonProperty("daypass")
        DAYPASS,

        @JsonProperty("free")
        FREE,

        @JsonProperty("open")
        OPEN,

        @JsonProperty("premium")
        PREMIUM
    }

    @Getter
    @Setter
    public static class SpotifyExplicitContent {
        @JsonProperty("filter_enabled")
        private Boolean filterEnabled;

        @JsonProperty("filter_locked")
        private Boolean filterLocked;

    }

}
