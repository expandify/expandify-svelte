package dev.kenowi.exportify.spotify.clients;

import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPrivateUser;
import dev.kenowi.exportify.spotify.interceptors.CredentialsInterceptor;
import dev.kenowi.exportify.spotify.interceptors.RateLimitInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "SpotifyUserClient",
        url = "https://api.spotify.com/v1/me",
        configuration = {CredentialsInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyUserClient {

    @GetMapping
    SpotifyPrivateUser getCurrentUser();


    @GetMapping
    SpotifyIdProjection getCurrentUserID(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader);

}
