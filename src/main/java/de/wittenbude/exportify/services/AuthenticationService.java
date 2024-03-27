package de.wittenbude.exportify.services;

import de.wittenbude.exportify.configuration.AuthenticationProperties;
import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.models.Credentials;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";
    private final AuthenticationProperties authenticationProperties;
    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;
    private final CredentialsService credentialsService;
    private final ApiTokenService apiTokenService;

    AuthenticationService(
            AuthenticationProperties authenticationProperties,
            JweEncoderService jweEncoderService,
            JweDecoderService jweDecoderService,
            CredentialsService credentialsService,
            ApiTokenService apiTokenService) {
        this.authenticationProperties = authenticationProperties;
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.credentialsService = credentialsService;
        this.apiTokenService = apiTokenService;
    }

    public URI buildAuthorizeURL(String redirectUri) {
        if (!UrlUtils.isValidRedirectUrl(redirectUri)) {
            throw new InvalidRedirectUriException("Provided redirect uri is invalid: %s".formatted(redirectUri));
        }
        String encryptedState = jweEncoderService.encode(REDIRECT_URI_CLAIM_KEY, redirectUri);
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
        String encryptedCode = jweEncoderService.encode(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);
        String redirectUri = jweDecoderService.decode(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);

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

        String spotifyCode = jweDecoderService.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);

        Credentials credentials = credentialsService.load(spotifyCode, authenticationProperties.getRedirectUri());
        return apiTokenService.createApiToken(credentials.getUser().getId());
    }

}
