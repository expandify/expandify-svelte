package de.wittenbude.exportify.auth;

import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAuthenticationClient",
        url = "https://accounts.spotify.com/api",
        configuration = {TokenFormRequestInterceptor.class})
interface SpotifyAuthenticationClient {

    @PostMapping(path = "/token")
    SpotifyTokenResponse token(@RequestParam(name = "code") String code,
                               @RequestParam(name = "redirect_uri") String redirectUri,
                               @RequestParam(name = "grant_type", defaultValue = "authorization_code") String grantType);


    @PostMapping("/token")
    SpotifyTokenResponse refresh(@RequestParam(name = "refresh_token") String refreshToken,
                                 @RequestParam(name = "grant_type", defaultValue = "refresh_token") String grantType);
}
