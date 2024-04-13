package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPrivateUser;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import io.quarkus.rest.client.reactive.NotBody;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.HttpHeaders;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.spotify.com/v1/me")
@RegisterProvider(CredentialsInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyUserClient {

    @GET
    SpotifyPrivateUser getCurrentUser();


    @GET
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer {accessToken}")
    SpotifyIdProjection getCurrentUserID(@NotBody String accessToken);

}
