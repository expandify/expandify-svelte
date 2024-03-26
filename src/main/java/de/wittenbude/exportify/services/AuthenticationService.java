package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.InvalidRedirectUriException;
import de.wittenbude.exportify.jwt.JweDecoder;
import de.wittenbude.exportify.jwt.JweEncoder;
import de.wittenbude.exportify.models.Credentials;
import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.properties.AuthenticationProperties;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
public class AuthenticationService {
    private static final String REDIRECT_URI_CLAIM_KEY = "redirect_uri";
    private static final String SPOTIFY_CODE_CLAIM_KEY = "code";
    public static final String USER_ID_CLAIM = OAuth2TokenIntrospectionClaimNames.SUB;
    private final AuthenticationProperties authenticationProperties;
    private final JweEncoder jweEncoder;
    private final JweDecoder jweDecoder;
    private final UserService userService;
    private final CredentialsService credentialsService;

    AuthenticationService(
            AuthenticationProperties authenticationProperties,
            JweEncoder jweEncoder,
            JweDecoder jweDecoder,
            UserService userService,
            CredentialsService credentialsService) {
        this.authenticationProperties = authenticationProperties;
        this.jweEncoder = jweEncoder;
        this.jweDecoder = jweDecoder;
        this.userService = userService;
        this.credentialsService = credentialsService;
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

        Credentials credentials = credentialsService.exchange(spotifyCode);
        PrivateUser privateUser = userService.getOrLoad(credentials.getAccessToken());

        credentials.setUser(privateUser);
        credentialsService.upsert(credentials);

        return jweEncoder.encode(USER_ID_CLAIM, privateUser.getId());
    }


    public AbstractAuthenticationToken convert(Jwt jwt) {
        String userID = jwt.getClaimAsString(USER_ID_CLAIM);
        return new JwtAuthenticationToken(jwt, null, userID);
    }

    public UUID getCurrentAuthenticatedUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw new ProviderNotFoundException("Current Authenticated User is not of type JwtAuthenticationToken");
        }

        return UUID.fromString(jwtAuthenticationToken.getName());
    }

}
