package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.exception.InvalidRedirectUriException;
import dev.kenowi.exportify.domain.service.jwt.JweDecoderService;
import dev.kenowi.exportify.domain.service.jwt.JweEncoderService;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";

    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final ApplicationEventPublisher eventBus;
    private final ExportifyUserRepository exportifyUserRepository;
    private final CredentialsService credentialsService;
    private final SpotifyUserClient spotifyUserClient;

    @Value("${spotify.client-id}")
    private String clientID;
    @Value("${spotify.scopes}")
    private Set<String> scopes;
    @Value("${spotify.redirect-uri}")
    private String redirectURI;



    AuthenticationService(SpotifyAuthenticationClient spotifyAuthenticationClient,
                          JweEncoderService jweEncoderService,
                          JweDecoderService jweDecoderService,
                          ApplicationEventPublisher eventBus,
                          CredentialsService credentialsService, ExportifyUserRepository exportifyUserRepository, SpotifyUserClient spotifyUserClient) {
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.eventBus = eventBus;
        this.credentialsService = credentialsService;
        this.exportifyUserRepository = exportifyUserRepository;
        this.spotifyUserClient = spotifyUserClient;
    }


    public URI buildAuthorizeURL(String userRedirectURI, boolean showDialog) {
        if (!UrlUtils.isValidRedirectUrl(userRedirectURI)) {
            throw new InvalidRedirectUriException("Provided redirect uri is invalid: %s".formatted(userRedirectURI));
        }
        String encryptedState = jweEncoderService.encode(REDIRECT_URI_CLAIM_KEY, userRedirectURI);
        return this.authorizationURL(encryptedState, showDialog);
    }

    public URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
        String redirectUri = jweDecoderService.decode(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);
        String encryptedCode = jweEncoderService.encode(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);

        if (!UrlUtils.isValidRedirectUrl(redirectUri)) {
            throw new InvalidRedirectUriException("Provided redirect uri is invalid: %s".formatted(redirectUri));
        }
        return this.callbackURI(redirectUri, codeParam, encryptedCode);
    }

    public String authenticateUser(String internalCode) {
        String spotifyCode = jweDecoderService.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectURI);

        String spotifyUserID = spotifyUserClient
                .getCurrentUserID("Bearer " + tokenResponse.getAccessToken())
                .getId();

        ExportifyUser user = exportifyUserRepository
                .findBySpotifyID(spotifyUserID)
                .orElseGet(() -> exportifyUserRepository.save(new ExportifyUser().setSpotifyUserID(spotifyUserID)));

        eventBus.publishEvent(new ExportifyUserCreatedEvent(user, tokenResponse));

        return jweEncoderService.encode(AuthenticatedUser.USER_ID_CLAIM, user.getId());
    }

    public AbstractAuthenticationToken parsePrincipal(Jwt source) {
        return new AuthenticatedUser(source, credentialsService, exportifyUserRepository);
    }

    private URI authorizationURL(String state, boolean showDialog) {
        return UriComponentsBuilder
                .fromUriString("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientID)
                .queryParam("scope", String.join(" ", scopes))
                .queryParam("redirect_uri", redirectURI)
                .queryParam("show_dialog", showDialog)
                .queryParam("state", state)
                .build()
                .toUri();
    }


    private URI callbackURI(String redirectURI, String paramKey, String paramValue) {
        return UriComponentsBuilder
                .fromUri(URI.create(redirectURI))
                .queryParam(paramKey, paramValue)
                .build()
                .toUri();
    }
}
