package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.component.RequestAuthorization;
import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import de.wittenbude.expandify.repositories.SpotifyUserRepository;
import de.wittenbude.expandify.services.spotifyapi.AuthorizedSpotifyApiRequestService;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Optional;

@Service
public class SpotifyUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyUserService.class);
    private final SpotifyUserRepository spotifyUserRepository;
    private final SpotifyApiRequest spotifyApiRequest;
    private final RequestAuthorization requestAuthorization;


    public SpotifyUserService(
            SpotifyUserRepository spotifyUserRepository,
            AuthorizedSpotifyApiRequestService spotifyApiRequest,
            RequestAuthorization requestAuthorization
    ) {
        this.spotifyUserRepository = spotifyUserRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.requestAuthorization = requestAuthorization;
    }


    public SpotifyUser loadCurrentFromSpotify() throws SpotifyWebApiException {
        User user = spotifyApiRequest.makeRequest(SpotifyApi::getCurrentUsersProfile);
        SpotifyUser spotifyUser = new SpotifyUser(user);
        spotifyUserRepository.save(spotifyUser);
        return spotifyUser;
    }

    public Optional<SpotifyUser> getCurrentFromDB() throws UnauthorizedException {
        return spotifyUserRepository.findById(requestAuthorization.spotifyUserId());
    }

}
