package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.credentials.api.CredentialsInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "SpotifyArtistsClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class})
public interface SpotifyArtistClient {
    @GetMapping("/me/following?type=artist")
    Map<String, SpotifyCursorPage<SpotifyArtist>> getFollowing(@RequestParam(required = false) String after,
                                                               @RequestParam(required = false) Integer limit);


    @GetMapping("/artists")
    Map<String, List<SpotifyArtist>> getArtists(@RequestParam String ids);
}
