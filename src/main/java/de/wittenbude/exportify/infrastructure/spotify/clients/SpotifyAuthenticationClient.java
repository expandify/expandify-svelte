package de.wittenbude.exportify.infrastructure.spotify.clients;

import de.wittenbude.exportify.infrastructure.spotify.RateLimitInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.TokenFormRequestInterceptor;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAuthenticationClient",
        url = "https://accounts.spotify.com/api/token",
        configuration = {TokenFormRequestInterceptor.class, RateLimitInterceptor.class})
public interface SpotifyAuthenticationClient {

    @PostMapping
    SpotifyTokenResponse token(@RequestParam("code") String code,
                               @RequestParam("redirect_uri") String redirectUri,
                               @RequestParam(name = "grant_type") String grantType);


    @PostMapping
    SpotifyTokenResponse refresh(@RequestParam("refresh_token") String refreshToken,
                                 @RequestParam(name = "grant_type") String grantType);

    default SpotifyTokenResponse token(String code, String redirectUri) {
        return this.token(code, redirectUri, "authorization_code");
    }

    default SpotifyTokenResponse refresh(String refreshToken) {
        return this.refresh(refreshToken, "refresh_token");
    }
}
