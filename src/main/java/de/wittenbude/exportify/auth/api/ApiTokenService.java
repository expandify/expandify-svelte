package de.wittenbude.exportify.auth.api;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import java.util.UUID;

public interface ApiTokenService {
    String userIdToToken(UUID userID);

    UUID getAuthenticatedUserId();

    void configureTokenParsing(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwt);

}
