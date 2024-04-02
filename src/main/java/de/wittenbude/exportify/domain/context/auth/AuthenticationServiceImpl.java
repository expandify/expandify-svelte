package de.wittenbude.exportify.domain.context.auth;

import de.wittenbude.exportify.domain.context.exportifyuser.ExportifyUserService;
import de.wittenbude.exportify.domain.context.spotifyuser.SpotifyUserService;
import de.wittenbude.exportify.domain.entities.ExportifyUser;
import de.wittenbude.exportify.domain.events.UserAuthenticatedEvent;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
class AuthenticationServiceImpl implements AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";


    private final String redirectURI;
    private final JweEncoderService jweEncoderService;
    private final JweDecoderService jweDecoderService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyUriService spotifyUriService;
    private final SpotifyUserService spotifyUserService;
    private final ExportifyUserService exportifyUserService;


    public AuthenticationServiceImpl(@Value("${spotify.redirect-uri}") String redirectURI,
                                     SpotifyAuthenticationClient spotifyAuthenticationClient,
                                     JweEncoderService jweEncoderService,
                                     JweDecoderService jweDecoderService,
                                     ApplicationEventPublisher eventBus,
                                     SpotifyUriService spotifyUriService, SpotifyUserService spotifyUserService, ExportifyUserService exportifyUserService) {
        this.redirectURI = redirectURI;
        this.jweEncoderService = jweEncoderService;
        this.jweDecoderService = jweDecoderService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.eventBus = eventBus;
        this.spotifyUriService = spotifyUriService;
        this.spotifyUserService = spotifyUserService;
        this.exportifyUserService = exportifyUserService;
    }


    public URI buildAuthorizeURL(String userRedirectURI) {
        String encryptedState = jweEncoderService.encode(REDIRECT_URI_CLAIM_KEY, userRedirectURI);
        return spotifyUriService.authorizationURL(encryptedState);
    }

    public URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
        String redirectUri = jweDecoderService.decode(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);
        String encryptedCode = jweEncoderService.encode(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);
        return spotifyUriService.callbackURI(redirectUri, codeParam, encryptedCode);
    }

    public String authenticateUser(String internalCode) {
        String spotifyCode = jweDecoderService.decode(internalCode, SPOTIFY_CODE_CLAIM_KEY);
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectURI);


        String spotifyUserID = spotifyUserService.loadSpotifyUserID(tokenResponse.getAccessToken());
        ExportifyUser user = exportifyUserService.findOrCreate(spotifyUserID);

        eventBus.publishEvent(new UserAuthenticatedEvent(tokenResponse, user, null));

        return jweEncoderService.encode(USER_ID_CLAIM, user.getId());
    }
}
