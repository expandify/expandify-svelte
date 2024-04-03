package de.wittenbude.exportify.domain.context.auth;

import de.wittenbude.exportify.domain.context.credentials.CredentialsRepository;
import de.wittenbude.exportify.domain.context.credentials.CredentialsService;
import de.wittenbude.exportify.domain.context.exportifyuser.ExportifyUserRepository;
import de.wittenbude.exportify.domain.context.exportifyuser.ExportifyUserService;
import de.wittenbude.exportify.domain.context.spotifyuser.SpotifyUserService;
import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.events.UserAuthenticatedEvent;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";



    @Value("${spotify.client-id}")
    private String clientID;
    @Value("${spotify.scopes}")
    private Set<String> scopes;
    @Value("${spotify.redirect-uri}")
    private String redirectURI;

    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyUserService spotifyUserService;
    private final ExportifyUserService exportifyUserService;
    private final CredentialsRepository credentialsRepository;
    private final CredentialsService credentialsService;
    private final ExportifyUserRepository exportifyUserRepository;


    public AuthenticationService(SpotifyAuthenticationClient spotifyAuthenticationClient,
                                 JweEncoderService jweEncoderService,
                                 JweDecoderService jweDecoderService,
                                 ApplicationEventPublisher eventBus,
                                 SpotifyUserService spotifyUserService,
                                 ExportifyUserService exportifyUserService,
                                 CredentialsRepository credentialsRepository,
                                 CredentialsService credentialsService,
                                 ExportifyUserRepository exportifyUserRepository) {
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.eventBus = eventBus;
        this.spotifyUserService = spotifyUserService;
        this.exportifyUserService = exportifyUserService;
        this.credentialsRepository = credentialsRepository;
        this.credentialsService = credentialsService;
        this.exportifyUserRepository = exportifyUserRepository;
    }


    public URI buildAuthorizeURL(String userRedirectURI) {
        String encryptedState = jweEncoderService.encode(REDIRECT_URI_CLAIM_KEY, userRedirectURI);
        return this.authorizationURL(encryptedState);
    }

    public URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
        String redirectUri = jweDecoderService.decode(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);
        String encryptedCode = jweEncoderService.encode(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);
        return this.callbackURI(redirectUri, codeParam, encryptedCode);
    }

    public String authenticateUser(String internalCode) {
        String spotifyCode = jweDecoderService.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectURI);


        String spotifyUserID = spotifyUserService.loadSpotifyUserID(tokenResponse.getAccessToken());
        ExportifyUser user = exportifyUserService.findOrCreate(spotifyUserID);

        eventBus.publishEvent(new UserAuthenticatedEvent(tokenResponse, user, null));

        return jweEncoderService.encode(AuthenticatedUser.USER_ID_CLAIM, user.getId());
    }

    public AbstractAuthenticationToken parsePrincipal(Jwt source) {
        return new AuthenticatedUser(source, credentialsRepository, credentialsService, exportifyUserRepository);
    }

    private URI authorizationURL(String state) {
        return UriComponentsBuilder
                .fromUriString("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientID)
                .queryParam("scope", String.join(" ", scopes))
                .queryParam("redirect_uri", redirectURI)
                .queryParam("show_dialog", true)
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
