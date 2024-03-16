package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.SpotifyCredentials;
import de.wittenbude.exportify.db.repositories.SpotifyCredentialsRepository;
import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.jwt.JweDecoder;
import de.wittenbude.exportify.jwt.JweEncoder;
import de.wittenbude.exportify.properties.AuthenticationProperties;
import de.wittenbude.exportify.spotify.clients.SpotifyAuthenticationClient;
import de.wittenbude.exportify.spotify.data.PrivateUser;
import de.wittenbude.exportify.spotify.data.TokenResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";
    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;
    private final AuthenticationProperties authenticationProperties;
    private final SpotifyAuthenticationClient spotifyAuthenticationClient;
    private final JweEncoder jweEncoder;
    private final JweDecoder jweDecoder;
    private final SpotifyCredentialsRepository spotifyCredentialsRepository;
    private final SpotifyUserService spotifyUserService;

    AuthenticationService(
            AuthenticationProperties authenticationProperties,
            SpotifyAuthenticationClient spotifyAuthenticationClient,
            JweEncoder jweEncoder,
            JweDecoder jweDecoder,
            SpotifyCredentialsRepository spotifyCredentialsRepository,
            SpotifyUserService spotifyUserService
    ) {
        this.authenticationProperties = authenticationProperties;
        this.spotifyAuthenticationClient = spotifyAuthenticationClient;
        this.jweEncoder = jweEncoder;
        this.jweDecoder = jweDecoder;
        this.spotifyCredentialsRepository = spotifyCredentialsRepository;
        this.spotifyUserService = spotifyUserService;
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

    public URI buildCodeURI(String spotifyCode, String encryptedRedirectUri, String codeParam) {
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

        TokenResponse spotifyToken = spotifyAuthenticationClient.token(
                SpotifyAuthenticationClient
                        .AuthorizationCodeForm
                        .builder()
                        .code(spotifyCode)
                        .redirectUri(authenticationProperties.getRedirectUri())
                        .build());


        PrivateUser privateUser = spotifyUserService.getCurrentUser(spotifyToken.getAccessToken());

        spotifyCredentialsRepository.upsert(
                SpotifyCredentials
                        .builder()
                        .accessToken(spotifyToken.getAccessToken())
                        .refreshToken(spotifyToken.getRefreshToken())
                        .scope(spotifyToken.getScope())
                        .expiresAt(Instant.now().plus(spotifyToken.getExpiresIn(), ChronoUnit.SECONDS))
                        .tokenType(spotifyToken.getTokenType())
                        .spotifyUserID(privateUser.getId())
                        .build());

        return jweEncoder.encode(USER_ID_CLAIM, privateUser.getId());
    }


    public AbstractAuthenticationToken convert(Jwt jwt) {
        String id = jwt.getClaimAsString(USER_ID_CLAIM);
        return new JwtAuthenticationToken(jwt, null, id);
    }

}
