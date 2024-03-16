package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.PrivateUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "SpotifyArtistsClient",
        url = "https://api.spotify.com/v1",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyArtistsClient {


    @GetMapping("/me")
    PrivateUser getCurrentUser();

    @GetMapping("/me")
    PrivateUser getCurrentUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
