package de.wittenbude.exportify.spotify.clients.configuration;

import de.wittenbude.exportify.request.CurrentAccessCredentials;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class AccessTokenInterceptor {
    private final CurrentAccessCredentials currentAccessCredentials;

    public AccessTokenInterceptor(CurrentAccessCredentials currentAccessCredentials) {
        this.currentAccessCredentials = currentAccessCredentials;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                return;
            }
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        };
    }

    private String getAccessToken() {
        if (currentAccessCredentials.isExpired()) {
            currentAccessCredentials.refreshCredentials();
        }
        return currentAccessCredentials.getSpotifyCredentials().getAccessToken();
    }

}
