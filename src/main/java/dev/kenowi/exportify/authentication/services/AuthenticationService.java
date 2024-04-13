package dev.kenowi.exportify.authentication.services;

import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.authentication.ExportifyUserCreatedEvent;
import dev.kenowi.exportify.authentication.entities.ExportifyUser;
import dev.kenowi.exportify.authentication.repositories.ExportifyUserRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyAuthenticationClient;
import dev.kenowi.exportify.spotify.clients.SpotifyUserClient;
import dev.kenowi.exportify.spotify.dao.SpotifyTokenResponse;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.util.Set;

@ApplicationScoped
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";

    private final JwtService jwtService;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final EventBus eventBus;
    private final ExportifyUserRepository exportifyUserRepository;
    private final SpotifyUserClient spotifyUserClient;

    @ConfigProperty(name = "spotify-auth.client-id")
    String clientID;
    @ConfigProperty(name = "spotify-auth.scopes")
    Set<String> scopes;
    @ConfigProperty(name = "spotify-auth.redirect-uri")
    String redirectURI;


    AuthenticationService(@RestClient SpotifyAuthenticationClient spotifyAuthenticationClient,
                          @RestClient SpotifyUserClient spotifyUserClient,
                          JwtService jwtService,
                          EventBus eventBus,
                          ExportifyUserRepository exportifyUserRepository) {
        this.jwtService = jwtService;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.eventBus = eventBus;
        this.exportifyUserRepository = exportifyUserRepository;
        this.spotifyUserClient = spotifyUserClient;
    }


    public URI buildAuthorizeURL(String userRedirectURI, boolean showDialog) {
        //TODO check if userRedirectURI is valid
        String encryptedState = jwtService.create(REDIRECT_URI_CLAIM_KEY, userRedirectURI);
        return this.authorizationURL(encryptedState, showDialog);
    }

    public URI buildCallbackURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
        String redirectUri = jwtService.parse(encryptedRedirectUri, REDIRECT_URI_CLAIM_KEY);
        String encryptedCode = jwtService.create(SPOTIFY_CODE_CLAIM_KEY, spotifyCode);

        //TODO check if redirectUri is valid
        return this.callbackURI(redirectUri, codeParam, encryptedCode);
    }

    public String authenticateUser(String internalCode) {
        String spotifyCode = jwtService.parse(internalCode, SPOTIFY_CODE_CLAIM_KEY);
        SpotifyTokenResponse tokenResponse = spotifyAuthenticationClient.token(spotifyCode, redirectURI);

        String spotifyUserID = spotifyUserClient
                .getCurrentUserID(tokenResponse.getAccessToken())
                .getId();

        ExportifyUser user = exportifyUserRepository
                .findBySpotifyID(spotifyUserID)
                .orElseGet(() -> exportifyUserRepository.save(new ExportifyUser().setSpotifyUserID(spotifyUserID)));


        eventBus.publish(ExportifyUserCreatedEvent.NAME, new ExportifyUserCreatedEvent(user, tokenResponse));

        return jwtService.create(AuthenticatedUser.USER_ID_CLAIM, user.getId());
    }

    private URI authorizationURL(String state, boolean showDialog) {
        return UriBuilder
                .fromUri("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientID)
                .queryParam("scope", String.join(" ", scopes))
                .queryParam("redirect_uri", redirectURI)
                .queryParam("show_dialog", showDialog)
                .queryParam("state", state)
                .build();
    }


    private URI callbackURI(String redirectURI, String paramKey, String paramValue) {
        return UriBuilder
                .fromUri(redirectURI)
                .queryParam(paramKey, paramValue)
                .build();
    }
}
