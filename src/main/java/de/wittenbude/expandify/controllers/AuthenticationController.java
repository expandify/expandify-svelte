package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.services.auth.AuthenticationService;
import de.wittenbude.expandify.services.auth.JwtService;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;


    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String redirectUrl) throws SpotifyWebApiException {

        URI redirect = authenticationService.spotifyAuthenticationUri(redirectUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirect);
        return new ResponseEntity<>(headers, HttpStatus.valueOf(302));
    }


    @GetMapping("/callback")
    public RedirectView callback(@RequestParam String code, @RequestParam String state) throws SpotifyWebApiException, MalformedURLException, URISyntaxException {
        String userId = authenticationService.authenticateUser(code);
        String jwt = jwtService.generateToken(userId);
        URIBuilder uriBuilder = new URIBuilder(state);
        uriBuilder.addParameter("token", jwt);
        return new RedirectView(uriBuilder.build().toURL().toString());
    }


}
