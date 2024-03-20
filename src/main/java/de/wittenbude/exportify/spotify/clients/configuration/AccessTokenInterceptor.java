package de.wittenbude.exportify.spotify.clients.configuration;

import de.wittenbude.exportify.request.CurrentValidCredentials;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class AccessTokenInterceptor {
    private final CurrentValidCredentials currentValidCredentials;

    public AccessTokenInterceptor(CurrentValidCredentials currentValidCredentials) {
        this.currentValidCredentials = currentValidCredentials;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                return;
            }
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + currentValidCredentials.get().getAccessToken());
        };
    }

}
