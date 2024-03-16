package de.wittenbude.exportify.spotify.clients.configuration;

import de.wittenbude.exportify.properties.AuthenticationProperties;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

public class TokenFormRequestInterceptor {
    private final AuthenticationProperties authenticationProperties;

    public TokenFormRequestInterceptor(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64EncodedClientIdAndSecret())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    private String base64EncodedClientIdAndSecret() {
        String toEncode = authenticationProperties.getClientID() + ":" + authenticationProperties.getClientSecret();
        return Base64
                .getEncoder()
                .encodeToString(toEncode.getBytes());
    }


}
