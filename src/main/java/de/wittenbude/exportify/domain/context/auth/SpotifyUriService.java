package de.wittenbude.exportify.domain.context.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@Service
class SpotifyUriService {

    @Value("${spotify.client-id}")
    String clientID;
    @Value("${spotify.scopes}")
    Set<String> scopes;
    @Value("${spotify.redirect-uri}")
    String redirectURI;


    public URI authorizationURL(String state) {
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


    public URI callbackURI(String redirectURI, String paramKey, String paramValue) {
        return UriComponentsBuilder
                .fromUri(URI.create(redirectURI))
                .queryParam(paramKey, paramValue)
                .build()
                .toUri();
    }
}
