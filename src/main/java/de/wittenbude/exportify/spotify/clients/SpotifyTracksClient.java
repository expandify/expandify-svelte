package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import de.wittenbude.exportify.spotify.data.SpotifySavedTrack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyTracksClient",
        url = "https://api.spotify.com/v1",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyTracksClient {


    @GetMapping("/me/following")
    SpotifyCursorPage<SpotifySavedTrack> getSaved(@RequestParam(required = false) Integer limit,
                                                  @RequestParam(required = false) Integer offset);
}
