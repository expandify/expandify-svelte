package dev.kenowi.exportify.infrastructure.spotify.clients;

import dev.kenowi.exportify.infrastructure.spotify.CredentialsInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.RateLimitInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyArtist;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifySavedTrack;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTrack;
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
