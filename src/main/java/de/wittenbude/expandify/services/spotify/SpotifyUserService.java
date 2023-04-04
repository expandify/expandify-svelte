package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.SpotifyUserPrivate;
import de.wittenbude.expandify.repositories.users.SpotifyUserPrivateDao;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import de.wittenbude.expandify.services.api.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

@Service
public class SpotifyUserService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyUserService.class);
    private final SpotifyApiRequestService spotifyApiRequest;
    private final AuthenticatedUserService authenticatedUserService;

    private final SpotifyUserPrivateDao spotifyUserPrivateDao;


    public SpotifyUserService(
            SpotifyApiRequestService spotifyApiRequest,
            AuthenticatedUserService authenticatedUserService,
            SpotifyUserPrivateDao spotifyUserPrivateDao) {
        this.spotifyApiRequest = spotifyApiRequest;
        this.authenticatedUserService = authenticatedUserService;
        this.spotifyUserPrivateDao = spotifyUserPrivateDao;
    }


    public SpotifyUserPrivate getCurrent() throws SpotifyWebApiException {
        return spotifyUserPrivateDao.find(authenticatedUserService.getUserId())
                .orElse(requestCurrent());
    }

    private SpotifyUserPrivate requestCurrent() throws SpotifyWebApiException {
        User user = spotifyApiRequest.makeRequest(SpotifyApi::getCurrentUsersProfile);
        SpotifyUserPrivate spotifyUser = new SpotifyUserPrivate(user);
        return spotifyUserPrivateDao.save(spotifyUser);
    }
}
