package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylist;
import dev.kenowi.exportify.spotify.dao.SpotifyPlaylistTrack;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyPlaylistClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyPlaylistClient {
    @GetMapping("/me/playlists")
    SpotifyPage<SpotifyIdProjection> getUserPlaylistIDs(@RequestParam("limit") Integer limit,
                                                        @RequestParam("offset") Integer offset);

    @GetMapping("/playlists/{playlist_id}")
    SpotifyPlaylist get(@PathVariable("playlist_id") String playlist_id);


    @GetMapping(value = "/playlists/{playlist_id}/tracks?fields=limit,next,offset,total,items(track(id,type))")
    SpotifyPage<SpotifyPlaylistTrack> getPlaylistItemIDs(@PathVariable("playlist_id") String playlist_id,
                                                         @RequestParam("limit") Integer limit,
                                                         @RequestParam("offset") Integer offset);
}
