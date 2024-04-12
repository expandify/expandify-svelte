package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyAlbum;
import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifySavedAlbum;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
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
    SpotifyPage<SpotifyIdProjection> getAlbumTrackIDs(@PathVariable("id") String id,
                                                      @RequestParam("limit") Integer limit,
                                                      @RequestParam("offset") Integer offset);


    @GetMapping("/albums/{id}")
    SpotifyAlbum get(@PathVariable("id") String id);
}
