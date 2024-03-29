package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.credentials.api.CredentialsInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import de.wittenbude.exportify.spotify.data.SpotifySavedAlbum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAlbumClient",
        url = "https://api.spotify.com/v1",
        configuration = {CredentialsInterceptor.class})
public interface SpotifyAlbumClient {
    @GetMapping("/me/albums")
    SpotifyPage<SpotifySavedAlbum> getSaved(@RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset);
}
