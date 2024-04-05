package dev.kenowi.exportify.infrastructure.spotify.clients;

import dev.kenowi.exportify.infrastructure.spotify.CredentialsInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.RateLimitInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyArtist;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyCursorPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "SpotifyArtistsClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyArtistClient {

    @GetMapping("/me/following?type=artist")
    Map<String, SpotifyCursorPage<SpotifyArtist>> getFollowing(@RequestParam("after") String after,
                                                               @RequestParam("limit") Integer limit);


    @GetMapping("/artists")
    Map<String, List<SpotifyArtist>> getArtists(@RequestParam("ids") String ids);
}
