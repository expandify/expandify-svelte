package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.models.SpotifyObjectType;
import de.wittenbude.exportify.spotify.clients.configuration.AccessTokenInterceptor;
import de.wittenbude.exportify.spotify.data.CursorPage;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import de.wittenbude.exportify.spotify.data.SpotifyPrivateUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "SpotifyUserClient",
        url = "https://api.spotify.com/v1/me",
        configuration = {AccessTokenInterceptor.class})
public interface SpotifyUserClient {

    @GetMapping
    SpotifyPrivateUser getCurrentUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @GetMapping
    SpotifyPrivateUser getCurrentUser();

    @GetMapping("/following")
    Map<String, CursorPage<SpotifyArtist>> getFollowing(@RequestParam SpotifyObjectType type,
                                                        @RequestParam(required = false) String after,
                                                        @RequestParam(required = false) Integer limit);



}
