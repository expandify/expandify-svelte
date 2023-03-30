package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.requestscope.CurrentUser;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class LibraryService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final LibraryRepository libraryRepository;
    private final CurrentUser currentUser;
    private final SpotifyUserService userService;

    public LibraryService(
            LibraryRepository libraryRepository,
            CurrentUser currentUser,
            SpotifyUserService userService
    ) {
        this.libraryRepository = libraryRepository;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    public Library createLibrary(
            List<SavedAlbum> savedAlbums,
            List<SavedTrack> savedTracks,
            List<Artist> followedArtists,
            List<PlaylistSimplified> playlists,
            boolean latest
    ) throws SpotifyWebApiException {
        Library library = new Library(
                savedAlbums,
                savedTracks,
                followedArtists,
                playlists,
                userService.loadCurrent(),
                Date.from(Instant.now()),
                latest
        );

        if (latest) {
            Library latestLib = getLatestOrCreate();
            library.setId(latestLib.getId());
        }

        return libraryRepository.save(library);
    }

    private Library getLatestOrCreate() throws SpotifyWebApiException {
        return libraryRepository
                .findAllByOwner_IdAndLatestTrue(currentUser.getSpotifyUserId())
                .orElse(new Library(true, null, userService.loadCurrent()));
    }


}
