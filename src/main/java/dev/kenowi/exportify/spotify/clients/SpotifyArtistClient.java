package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyArtist;
import dev.kenowi.exportify.spotify.dao.SpotifyCursorPage;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Map;

@RegisterRestClient(baseUri = "https://api.spotify.com/v1")
@RegisterProvider(CredentialsInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyArtistClient {

    @GET
    @Path("/me/following")
    @ClientQueryParam(name = "type", value = "artist")
    Map<String, SpotifyCursorPage<SpotifyArtist>> getFollowing(@QueryParam("after") String after,
                                                               @QueryParam("limit") Integer limit);


    @GET
    @Path("/artists")
    Map<String, List<SpotifyArtist>> getArtists(@QueryParam("ids") String ids);
}
