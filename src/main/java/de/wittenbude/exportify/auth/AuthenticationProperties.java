package de.wittenbude.exportify.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;


@Getter
@Setter
@Component
@ConfigurationProperties("spotify-auth")
class AuthenticationProperties {

    private String clientID;
    private String clientSecret;
    private Set<String> scopes;
    private String redirectUri;
}
