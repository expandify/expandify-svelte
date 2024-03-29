package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import de.wittenbude.exportify.spotify.data.SpotifySavedTrack;
import de.wittenbude.exportify.spotify.data.SpotifyTrack;
import de.wittenbude.exportify.spotify.data.SpotifyTrackSimplified;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyTracksClient",
        url = "https://api.spotify.com/v1",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyTracksClient {


    @GetMapping(value = "/me/tracks")
    SpotifyPage<SpotifySavedTrack> getSaved(@RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset);


    @GetMapping(value = "/albums/{id}/tracks")
    SpotifyPage<SpotifyTrackSimplified> getAlbumTracks(@PathVariable String id,
                                                       @RequestParam(required = false) Integer limit,
                                                       @RequestParam(required = false) Integer offset);

    @GetMapping(value = "/tracks/{id}")
    SpotifyTrack get(@PathVariable String id);
}
