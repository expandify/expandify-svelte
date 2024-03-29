package de.wittenbude.exportify.credentials.api;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public interface CurrentValidCredentials {

    SpotifyCredentials get();
}
