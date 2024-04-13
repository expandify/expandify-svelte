package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylist;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylistTrack;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.spotify.com/v1")
@RegisterProvider(CredentialsInterceptor.class)
@RegisterProvider(RateLimitInterceptor.class)
public interface SpotifyPlaylistClient {

    @GET
    @Path("/me/playlists")
    SpotifyPage<SpotifyIdProjection> getUserPlaylistIDs(@QueryParam("limit") Integer limit,
                                                        @QueryParam("offset") Integer offset);

    @GET
    @Path("/playlists/{playlist_id}")
    SpotifyPlaylist get(@PathParam("playlist_id") String playlist_id);


    @GET
    @Path(value = "/playlists/{playlist_id}/tracks")
    @ClientQueryParam(name = "fields", value = "limit,next,offset,total,items(added_at,is_local,added_by(id,type),track(id,type))")
    SpotifyPage<SpotifyPlaylistTrack> getPlaylistItemIDs(@PathParam("playlist_id") String playlist_id,
                                                         @QueryParam("limit") Integer limit,
                                                         @QueryParam("offset") Integer offset);
}
