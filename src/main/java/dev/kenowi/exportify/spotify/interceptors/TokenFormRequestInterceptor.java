package dev.kenowi.exportify.spotify.interceptors;

import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

//@Provider
public class TokenFormRequestInterceptor {

    private final String base64EncodedClientIdAndSecret;

    public TokenFormRequestInterceptor(@Value("${spotify.client-id}") String clientID,
                                       @Value("${spotify.client-secret}") String clientSecret) {
        this.base64EncodedClientIdAndSecret = Base64
                .getEncoder()
                .encodeToString((clientID + ":" + clientSecret).getBytes());
    }

    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64EncodedClientIdAndSecret)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }
}
