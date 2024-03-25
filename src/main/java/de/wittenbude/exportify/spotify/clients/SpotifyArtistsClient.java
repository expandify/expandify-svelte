package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.models.ObjectType;
import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "SpotifyArtistsClient",
        url = "https://api.spotify.com/v1",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyArtistsClient {
    @GetMapping("/me/following")
    Map<String, SpotifyCursorPage<SpotifyArtist>> getFollowing(@RequestParam ObjectType type,
                                                               @RequestParam(required = false) String after,
                                                               @RequestParam(required = false) Integer limit);
}
