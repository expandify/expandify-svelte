package de.wittenbude.exportify.credentials.api;

import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import lombok.SneakyThrows;
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

    @Bean
    public Retryer retryer() {
        // TODO validate if this works (e.g. Logging)
        return new Retryer() {
            @Override
            public void continueOrPropagate(RetryableException e) {
                try {
                    Thread.sleep(e.retryAfter());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }

            @Override
            @SneakyThrows
            public Retryer clone() {
                super.clone();
                return this;
            }
        };
    }

}
