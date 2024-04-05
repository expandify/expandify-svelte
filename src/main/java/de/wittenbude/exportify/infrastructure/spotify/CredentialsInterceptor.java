package de.wittenbude.exportify.infrastructure.spotify;

import de.wittenbude.exportify.domain.context.auth.AuthenticatedUser;
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
