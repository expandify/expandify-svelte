package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@RestController
@RequestMapping()
@Slf4j
public class AuthenticationController {

    public static final String AUTHORIZATION_PATH = "/authorize";
    public static final String AUTHORIZATION_CALLBACK_PATH = "/authorize/callback";
    public static final String AUTHENTICATION_TOKEN_PATH = "/authorize/token";
    public static final String CODE_REQUEST_PARAM = "code";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(AUTHORIZATION_PATH)
    public RedirectView authorize(@RequestParam(name = "redirect_uri", defaultValue = AUTHENTICATION_TOKEN_PATH) String redirectURI) {
        return new RedirectView(authenticationService.buildAuthorizeURL(redirectURI).toString());
    }

    @GetMapping(AUTHORIZATION_CALLBACK_PATH)
    public RedirectView callback(@RequestParam(name = "code") String spotifyCode,
                                 @RequestParam(name = "state") String encryptedRedirectUri) {
        URI redirectUri = authenticationService.buildCodeURI(spotifyCode, encryptedRedirectUri, CODE_REQUEST_PARAM);
        return new RedirectView(redirectUri.toString());
    }

    @GetMapping(AUTHENTICATION_TOKEN_PATH)
    public String token(@RequestParam(CODE_REQUEST_PARAM) String internalCode) {
        return authenticationService.authenticateUser(internalCode);
    }
}
