package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "SpotifyUserClient",
        url = "https://api.spotify.com/v1/me",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyUserClient {

    @GetMapping
    SpotifyPrivateUser getCurrentUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @GetMapping
    SpotifyPrivateUser getCurrentUser();

}
