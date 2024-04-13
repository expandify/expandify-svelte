package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifySavedTrack;
import dev.kenowi.exportify.spotify.dao.SpotifyTrack;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Map;

@RegisterRestClient(baseUri = "https://api.spotify.com/v1")
@RegisterProvider(CredentialsInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyTrackClient {


    @GET
    @Path(value = "/me/tracks")
    SpotifyPage<SpotifySavedTrack> getSaved(@QueryParam("limit") Integer limit,
                                            @QueryParam("offset") Integer offset);

    @GET
    @Path(value = "/tracks/{id}")
    SpotifyTrack get(@PathParam("id") String id);


    @GET
    @Path("/tracks")
    Map<String, List<SpotifyTrack>> getTracks(@QueryParam("ids") String ids);
}
