package dev.kenowi.exportify.infrastructure.spotify.clients;

import dev.kenowi.exportify.infrastructure.spotify.RateLimitInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.TokenFormRequestInterceptor;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAuthenticationClient",
        url = "https://accounts.spotify.com/api/token",
        configuration = {TokenFormRequestInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyAuthenticationClient {

    @PostMapping("?grant_type=authorization_code")
    SpotifyTokenResponse token(@RequestParam("code") String code,
                               @RequestParam("redirect_uri") String redirectUri);


    @PostMapping("?grant_type=refresh_token")
    SpotifyTokenResponse refresh(@RequestParam("refresh_token") String refreshToken);


}
