package de.wittenbude.exportify.auth;

import de.wittenbude.exportify.auth.api.AuthenticationService;
import de.wittenbude.exportify.credentials.api.SpotifyCredentials;
import de.wittenbude.exportify.credentials.api.SpotifyCredentialsService;
import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
import de.wittenbude.exportify.user.api.ExportifyUser;
import de.wittenbude.exportify.user.api.ExportifyUserService;
import de.wittenbude.exportify.user.api.PrivateSpotifyUser;
import de.wittenbude.exportify.user.api.SpotifyUserService;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
class AuthenticationServiceImpl implements AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";
    private final AuthenticationProperties authenticationProperties;
    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;
    private final ApiTokenServiceImpl apiTokenService;
    private final SpotifyUserService spotifyUserService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final ExportifyUserService exportifyUserService;
    private final SpotifyCredentialsService spotifyCredentialsService;

    AuthenticationServiceImpl(
            AuthenticationProperties authenticationProperties,
            JweEncoderService jweEncoderService,
            JweDecoderService jweDecoderService,
            ApiTokenServiceImpl apiTokenService,
            SpotifyUserService spotifyUserService,
            SpotifyAuthenticationClient spotifyAuthenticationClient,
            ExportifyUserService exportifyUserService,
            SpotifyCredentialsService spotifyCredentialsService) {
        this.authenticationProperties = authenticationProperties;
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.apiTokenService = apiTokenService;
        this.spotifyUserService = spotifyUserService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.exportifyUserService = exportifyUserService;
        this.spotifyCredentialsService = spotifyCredentialsService;
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

    @Override
    public String authenticateUser(String internalCode) {
        String spotifyCode = jweDecoderService.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);
        String redirectUri = authenticationProperties.getRedirectUri();
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectUri, "authorization_code");

        PrivateSpotifyUser privateSpotifyUser = spotifyUserService.loadSpotifyUser(tokenResponse.getAccessToken());
        String spotifyID = privateSpotifyUser.getSpotifyID();
        ExportifyUser user = exportifyUserService.findOrCreate(spotifyID);
        SpotifyCredentials spotifyCredentials = spotifyCredentialsService.link(tokenResponse, user);

        return apiTokenService.userIdToToken(spotifyCredentials.getExportifyUser().getId());
    }

    @Override
    public SpotifyCredentials refreshToken(SpotifyCredentials credentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.refresh(credentials.getRefreshToken(), "refresh_token");

        return spotifyCredentialsService.refreshLink(tokenResponse, credentials);
    }

}
