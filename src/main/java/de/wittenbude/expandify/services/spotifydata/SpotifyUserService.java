package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.repositories.SpotifyUserRepository;
import de.wittenbude.expandify.requestscope.CurrentUser;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Optional;

@Service
public class SpotifyUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyUserService.class);
    private final SpotifyUserRepository spotifyUserRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final CurrentUser currentUser;


    public SpotifyUserService(
            SpotifyUserRepository spotifyUserRepository,
            SpotifyApiRequestService spotifyApiRequest,
            CurrentUser currentUser
    ) {
        this.spotifyUserRepository = spotifyUserRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.currentUser = currentUser;
    }


    public SpotifyUser loadCurrent() throws SpotifyWebApiException {
        return spotifyUserRepository
                .findById(currentUser.getSpotifyUserId())
                .orElse(requestCurrent());
    }

    private SpotifyUser requestCurrent() throws SpotifyWebApiException {
        User user = spotifyApiRequest.makeRequest(SpotifyApi::getCurrentUsersProfile);
        SpotifyUser spotifyUser = new SpotifyUser(user);
        spotifyUserRepository.save(spotifyUser);
        return spotifyUser;
    }
}
