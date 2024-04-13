package dev.kenowi.exportify.spotify.interceptors;

import dev.kenowi.exportify.authentication.AuthenticatedUser;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

public class CredentialsInterceptor implements ClientRequestFilter {


    public CredentialsInterceptor() {
    }

    @Override
    public void filter(ClientRequestContext requestContext) {

        if (requestContext.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return;
        }
        String accessToken = AuthenticatedUser.current().getCredentials().getAccessToken();
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }
}