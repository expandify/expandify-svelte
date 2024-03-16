package de.wittenbude.exportify.spotify.clients;

import de.wittenbude.exportify.spotify.clients.configuration.TokenFormRequestInterceptor;
import de.wittenbude.exportify.spotify.data.TokenResponse;
import feign.Param;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "SpotifyAuthenticationClient",
        url = "https://accounts.spotify.com/api",
        configuration = {TokenFormRequestInterceptor.class})
public interface SpotifyAuthenticationClient {

    @Getter
    @Builder
    class AuthorizationCodeForm {
        @Param("code")
        private final String code;

        @Param("redirect_uri")
        private final String redirectUri;

        @Param("grant_type")
        private final String grantType = "authorization_code";
    }

    @Getter
    @Builder
    class RefreshTokenForm {
        @Param("refresh_token")
        private final String refreshToken;

        @Param("grant_type")
        private final String grantType = "refresh_token";
    }

    @PostMapping(path = "/token")
    TokenResponse token(@SpringQueryMap AuthorizationCodeForm authorizationCodeForm);


    @PostMapping("/token")
    TokenResponse refresh(@SpringQueryMap RefreshTokenForm refreshTokenForm);
}
