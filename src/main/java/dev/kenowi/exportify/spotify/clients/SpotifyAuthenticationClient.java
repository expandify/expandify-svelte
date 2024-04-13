package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import dev.kenowi.exportify.spotify.interceptors.TokenFormRequestInterceptor;
import io.quarkus.rest.client.reactive.ClientFormParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/token")
@RegisterRestClient(baseUri = "https://accounts.spotify.com/api")
@RegisterProvider(TokenFormRequestInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyAuthenticationClient {


    @POST
    @ClientFormParam(name = "grant_type", value = "authorization_code")
    SpotifyTokenResponse token(@FormParam("code") String code,
                               @FormParam("redirect_uri") String redirectUri);


    @POST
    @ClientFormParam(name = "grant_type", value = "refresh_token")
    SpotifyTokenResponse refresh(@FormParam("refresh_token") String refreshToken);


}
