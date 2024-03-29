package de.wittenbude.exportify.auth;

import de.wittenbude.exportify.auth.api.ApiTokenService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ApiTokenServiceImpl implements ApiTokenService {

    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;
    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;

    public ApiTokenServiceImpl(JweEncoderService jweEncoderService,
                               JweDecoderService jweDecoderService) {
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
    }

    public String userIdToToken(UUID userID) {
        return jweEncoderService.encode(USER_ID_CLAIM, userID);
    }

    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
        }

        return UUID.fromString(jwtAuthenticationToken.getName());
    }

    public void configureTokenParsing(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwt) {
        jwt.decoder(jweDecoderService::decode)
                .jwtAuthenticationConverter(this::parseApiToken);
    }

    private AbstractAuthenticationToken parseApiToken(Jwt jwt) {
        String userID = jwt.getClaimAsString(USER_ID_CLAIM);
        return new JwtAuthenticationToken(jwt, null, userID);
    }

}
