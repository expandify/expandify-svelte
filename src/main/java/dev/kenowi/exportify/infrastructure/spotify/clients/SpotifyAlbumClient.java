package dev.kenowi.exportify.infrastructure.spotify.clients;

import dev.kenowi.exportify.infrastructure.spotify.CredentialsInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.RateLimitInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifySavedAlbum;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTrackSimplified;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAlbumClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyAlbumClient {
    @GetMapping("/me/albums")
    SpotifyPage<SpotifySavedAlbum> getSaved(@RequestParam("limit") Integer limit,
                                            @RequestParam("offset") Integer offset);


    @GetMapping(value = "/albums/{id}/tracks")
    SpotifyPage<SpotifyTrackSimplified> getAlbumTracks(@PathVariable("id") String id,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam("offset") Integer offset);
}
