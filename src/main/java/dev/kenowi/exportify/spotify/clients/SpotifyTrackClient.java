package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import dev.kenowi.exportify.spotify.dao.SpotifySavedTrack;
import dev.kenowi.exportify.spotify.dao.SpotifyTrack;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "SpotifyTracksClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyTrackClient {


    @GetMapping("/me/tracks")
    SpotifyPage<SpotifySavedTrack> getSaved(@RequestParam("limit") Integer limit,
                                            @RequestParam("offset") Integer offset);

    @GetMapping("/tracks/{id}")
    SpotifyTrack get(@PathVariable("id") String id);

    @GetMapping("/tracks")
    Map<String, List<SpotifyTrack>> getTracks(@RequestParam("ids") String ids);
}
