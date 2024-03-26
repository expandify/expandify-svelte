package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.jwt.JweDecoder;
import de.wittenbude.exportify.jwt.JweEncoder;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.properties.AuthenticationProperties;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";
    private final AuthenticationProperties authenticationProperties;
    private final JweEncoder jweEncoder;
    private final JweDecoder jweDecoder;
    private final CredentialsService credentialsService;
    private final ApiTokenService apiTokenService;

    AuthenticationService(
            AuthenticationProperties authenticationProperties,
            JweEncoder jweEncoder,
            JweDecoder jweDecoder,
            CredentialsService credentialsService,
            ApiTokenService apiTokenService) {
        this.authenticationProperties = authenticationProperties;
        this.jweEncoder = jweEncoder;
        this.jweDecoder = jweDecoder;
        this.credentialsService = credentialsService;
        this.apiTokenService = apiTokenService;
    }

    public URI buildAuthorizeURL(String redirectUri) {
        if (!UrlUtils.isValidRedirectUrl(redirectUri)) {
            throw new InvalidRedirectUriException("Provided redirect uri is invalid: %s".formatted(redirectUri));
        }
        String encryptedState = jweEncoder.encode(REDIRECT_URI_CLAIM_KEY, redirectUri);
        return UriComponentsBuilder
                .fromUriString("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", authenticationProperties.getClientID())
                .queryParam("scope", String.join(" ", authenticationProperties.getScopes()))
                .queryParam("redirect_uri", authenticationProperties.getRedirectUri())
                .queryParam("show_dialog", true)
                .queryParam("state", encryptedState)
                .build()
                .toUri();
    }

    public URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
        String encryptedCode = jweEncoder.encode(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);
        String redirectUri = jweDecoder.decode(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);

        if (!UrlUtils.isValidRedirectUrl(redirectUri)) {
            throw new InvalidRedirectUriException("Provided redirect uri is invalid: %s".formatted(redirectUri));
        }
        return UriComponentsBuilder
                .fromUri(URI.create(redirectUri))
                .queryParam(codeParam, encryptedCode)
                .build()
                .toUri();
    }


    public String authenticateUser(String internalCode) {

        String spotifyCode = jweDecoder.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);

        Credentials credentials = credentialsService.load(spotifyCode, authenticationProperties.getRedirectUri());
        return apiTokenService.createApiToken(credentials.getUser().getId());
    }

}
