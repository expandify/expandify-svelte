package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyLibrary;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedAlbum;
import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.requestscope.CurrentUser;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class SpotifyLibraryService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final LibraryRepository libraryRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final CurrentUser currentUser;
    private final SpotifyUserService userService;

    public SpotifyLibraryService(
            LibraryRepository libraryRepository,
            SpotifyApiRequestService spotifyApiRequest,
            CurrentUser currentUser,
            SpotifyUserService userService
    ) {
        this.libraryRepository = libraryRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    public List<SpotifySavedAlbum> saveAlbumsToLatest(List<SpotifySavedAlbum> albums) throws SpotifyWebApiException {
        SpotifyLibrary libraryLatest = getLatestOrCreate();

        libraryLatest.setSavedAlbums(albums);
        return libraryRepository.save(libraryLatest).getSavedAlbums();
    }


    private SpotifyLibrary getLatestOrCreate() throws SpotifyWebApiException {
        return libraryRepository
                .findAllByOwner_IdAndLatestTrue(currentUser.getSpotifyUserId())
                .orElse(new SpotifyLibrary(true, null, userService.getOrLoadCurrent()));
    }
}
