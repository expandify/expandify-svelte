package dev.kenowi.exportify.spotify.interceptors;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Base64;


public class TokenFormRequestInterceptor implements ClientRequestFilter {

    private final String base64EncodedClientIdAndSecret;

    public TokenFormRequestInterceptor(@ConfigProperty(name = "spotify-auth.client-id") String clientID,
                                       @ConfigProperty(name = "spotify-auth.client-secret") String clientSecret) {
        this.base64EncodedClientIdAndSecret = Base64
                .getEncoder()
                .encodeToString((clientID + ":" + clientSecret).getBytes());
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic " + base64EncodedClientIdAndSecret);
        //requestContext.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
    }
}
