package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.domain.service.auth.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorize")
class AuthenticationController {

    public static final String CODE_REQUEST_PARAM = "code";
    private final AuthenticationService authenticationService;


    AuthenticationController(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Void> authorize(@RequestParam(name = "redirect_uri", defaultValue = "/authorize/token") String redirectURI) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, authenticationService.buildAuthorizeURL(redirectURI).toString())
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam("code") String spotifyCode,
                                         @RequestParam("state") String encryptedRedirectUri) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, authenticationService
                        .buildCallbackURI(spotifyCode, encryptedRedirectUri, CODE_REQUEST_PARAM)
                        .toString())
                .build();
    }

    @GetMapping("/token")
    public String authenticateUser(@RequestParam(CODE_REQUEST_PARAM) String internalCode) {
        return authenticationService.authenticateUser(internalCode);
    }
}
