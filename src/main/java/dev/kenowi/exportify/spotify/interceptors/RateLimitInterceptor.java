package dev.kenowi.exportify.spotify.interceptors;

import feign.RetryableException;
import feign.Retryer;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;

public class RateLimitInterceptor {

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
