package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.services.auth.AuthenticationService;
import de.wittenbude.expandify.services.auth.JwtService;
import de.wittenbude.expandify.services.spotifydata.SpotifyUserService;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;
import java.net.URI;

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
    public ResponseEntity<Void> login() throws SpotifyWebApiException {

        URI redirect = authenticationService.spotifyAuthenticationUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirect);
        return new ResponseEntity<>(headers, HttpStatus.valueOf(302));
    }


    @GetMapping("/callback")
    public String callback(@RequestParam String code) throws SpotifyWebApiException {
        User user = authenticationService.authenticateUser(code);
        return jwtService.generateToken(user);
    }


}
