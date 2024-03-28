package de.wittenbude.exportify.services;

import de.wittenbude.exportify.configuration.AuthenticationProperties;
import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.models.ExportifyUser;
import de.wittenbude.exportify.models.PrivateSpotifyUser;
import de.wittenbude.exportify.models.SpotifyCredentials;
import de.wittenbude.exportify.models.converter.CredentialsConverter;
import de.wittenbude.exportify.repositories.CredentialsRepository;
import de.wittenbude.exportify.repositories.ExportifyUserRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
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
    private final ApiTokenService apiTokenService;
    private final SpotifyUserService spotifyUserService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final CredentialsRepository credentialsRepository;
    private final ExportifyUserRepository exportifyUserRepository;

    AuthenticationService(
            AuthenticationProperties authenticationProperties,
            JweEncoderService jweEncoderService,
            JweDecoderService jweDecoderService,
            ApiTokenService apiTokenService,
            SpotifyUserService spotifyUserService,
            SpotifyAuthenticationClient spotifyAuthenticationClient,
            CredentialsRepository credentialsRepository,
            ExportifyUserRepository exportifyUserRepository) {
        this.authenticationProperties = authenticationProperties;
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.apiTokenService = apiTokenService;
        this.spotifyUserService = spotifyUserService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.credentialsRepository = credentialsRepository;
        this.exportifyUserRepository = exportifyUserRepository;
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
        String redirectUri = authenticationProperties.getRedirectUri();
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectUri, "authorization_code");

        PrivateSpotifyUser privateSpotifyUser = spotifyUserService.loadSpotifyUser(tokenResponse.getAccessToken());
        String spotifyID = privateSpotifyUser.getSpotifyID();
        ExportifyUser user = exportifyUserRepository
                .findBySpotifyID(spotifyID)
                .orElseGet(() -> exportifyUserRepository.save(new ExportifyUser().setSpotifyUserID(spotifyID)));


        SpotifyCredentials spotifyCredentials = credentialsRepository.upsert(CredentialsConverter.from(tokenResponse).setExportifyUser(user));

        return apiTokenService.createApiToken(spotifyCredentials.getExportifyUser().getId());
    }

    public SpotifyCredentials refresh(SpotifyCredentials currentCredentials) {
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.refresh(currentCredentials.getRefreshToken(), "refresh_token");

        return credentialsRepository.save(CredentialsConverter
                .from(tokenResponse)
                .setExportifyUser(currentCredentials.getExportifyUser())
                .setId(currentCredentials.getId()));


    }


}
