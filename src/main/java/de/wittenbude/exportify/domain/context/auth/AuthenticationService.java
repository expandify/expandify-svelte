package de.wittenbude.exportify.domain.context.auth;

import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;

import java.net.URI;

public interface AuthenticationService {
    String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;

    URI buildAuthorizeURL(String userRedirectURI);

    URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam);

    String authenticateUser(String internalCode);
}
