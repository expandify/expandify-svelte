package de.wittenbude.exportify.infrastructure.spotify;

import de.wittenbude.exportify.domain.context.credentials.CurrentValidCredentials;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class CredentialsInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor(CurrentValidCredentials currentValidCredentials) {
        return requestTemplate -> {
            if (requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                return;
            }
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + currentValidCredentials.get().getAccessToken());
        };
    }
}
