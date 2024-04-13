package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyAlbum;
import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifySavedAlbum;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.spotify.com/v1")
@RegisterProvider(CredentialsInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyAlbumClient {

    @GET
    @Path("/me/albums")
    SpotifyPage<SpotifySavedAlbum> getSaved(@QueryParam("limit") Integer limit,
                                            @QueryParam("offset") Integer offset);


    @GET
    @Path(value = "/albums/{id}/tracks")
    SpotifyPage<SpotifyIdProjection> getAlbumTrackIDs(@PathParam("id") String id,
                                                    @QueryParam("limit") Integer limit,
                                                    @QueryParam("offset") Integer offset);

    @GET
    @Path("/albums/{id}")
    SpotifyAlbum get(@PathParam("id") String id);
}
