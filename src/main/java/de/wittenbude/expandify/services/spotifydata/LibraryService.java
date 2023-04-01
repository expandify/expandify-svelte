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
            List<PlaylistSimplified> playlists
    ) throws SpotifyWebApiException {
        Library library = new Library(
                savedAlbums,
                savedTracks,
                followedArtists,
                playlists,
                userService.loadCurrent(),
                Date.from(Instant.now())
        );

        return libraryRepository.save(library);
    }

    public List<Library> getAll() {
        return libraryRepository.findAllByOwner_Id(currentUser.getSpotifyUserId());
    }


    public Library getById(String id) {
        return libraryRepository.findById(id).orElse(null);
    }
}
