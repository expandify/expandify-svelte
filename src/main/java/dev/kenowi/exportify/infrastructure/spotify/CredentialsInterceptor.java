package dev.kenowi.exportify.infrastructure.spotify;

import dev.kenowi.exportify.domain.service.auth.AuthenticatedUser;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class CredentialsInterceptor {


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
                return;
            }
            String accessToken = AuthenticatedUser.getSecurityContext().getCredentials().getAccessToken();
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        };
    }
}
