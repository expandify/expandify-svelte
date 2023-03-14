package de.wittenbude.expandify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@Configuration
public class SpotifyApiConfig {
    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;
    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    /**
     * Creates an injectable SpotifyApi with all available credentials set.
     * @return The SpotifyApi
     */
    @Bean
    public SpotifyApi registeredSpotifyApi() {
        return new SpotifyApi
                .Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .setRedirectUri(URI.create(this.redirectUri))
                .build();
    }
}
