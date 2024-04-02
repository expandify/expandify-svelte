package de.wittenbude.exportify.infrastructure.spotify.clients;

import de.wittenbude.exportify.infrastructure.spotify.CredentialsInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.RateLimitInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPage;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifySavedTrack;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTrack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
}
