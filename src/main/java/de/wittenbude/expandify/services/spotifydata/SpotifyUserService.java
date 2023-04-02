package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

@Service
public class SpotifyUserService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyUserService.class);
    private final PersistenceService persistenceService;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final AuthenticatedUserData authenticatedUserData;


    public SpotifyUserService(
            PersistenceService persistenceService,
            SpotifyApiRequestService spotifyApiRequest,
            AuthenticatedUserData authenticatedUserData
    ) {
        this.persistenceService = persistenceService;
        this.spotifyApiRequest = spotifyApiRequest;
        this.authenticatedUserData = authenticatedUserData;
    }


    public SpotifyUser getCurrent() throws SpotifyWebApiException {
        return persistenceService.find(authenticatedUserData)
                .orElse(requestCurrent());
    }

    private SpotifyUser requestCurrent() throws SpotifyWebApiException {
        User user = spotifyApiRequest.makeRequest(SpotifyApi::getCurrentUsersProfile);
        SpotifyUser spotifyUser = new SpotifyUser(user);
        return persistenceService.save(spotifyUser);
    }
}
