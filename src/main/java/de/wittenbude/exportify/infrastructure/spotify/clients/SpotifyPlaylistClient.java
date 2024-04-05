package de.wittenbude.exportify.infrastructure.spotify.clients;

import de.wittenbude.exportify.infrastructure.spotify.CredentialsInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.RateLimitInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPage;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylist;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylistSimplified;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylistTrack;
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
    SpotifyPage<SpotifyPlaylistSimplified> getUser(@RequestParam("limit") Integer limit,
                                                   @RequestParam("offset") Integer offset);

    @GetMapping("/playlists/{playlist_id}")
    SpotifyPlaylist get(@PathVariable("playlist_id") String playlist_id);


    @GetMapping(value = "/playlists/{playlist_id}/tracks")
    SpotifyPage<SpotifyPlaylistTrack> getTracks(@PathVariable("playlist_id") String playlist_id,
                                                @RequestParam("limit") Integer limit,
                                                @RequestParam("offset") Integer offset);
}
