package de.wittenbude.exportify.auth.api;

import de.wittenbude.exportify.credentials.api.SpotifyCredentials;

import java.net.URI;


public interface AuthenticationService {
    URI buildAuthorizeURL(String redirectUri);

    URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam);

    String authenticateUser(String internalCode);

    SpotifyCredentials refreshToken(SpotifyCredentials credentials);

}
