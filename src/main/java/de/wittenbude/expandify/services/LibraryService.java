package de.wittenbude.expandify.services;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import de.wittenbude.expandify.services.spotifydata.SpotifyUserService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class LibraryService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final LibraryRepository libraryRepository;
    private final AuthenticatedUserData authenticatedUserData;
    private final SpotifyUserService userService;

    public LibraryService(
            LibraryRepository libraryRepository,
            AuthenticatedUserData authenticatedUserData,
            SpotifyUserService userService
    ) {
        this.libraryRepository = libraryRepository;
        this.authenticatedUserData = authenticatedUserData;
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
                userService.getCurrent(),
                Date.from(Instant.now())
        );

        return libraryRepository.save(library);
    }

    public List<Library> getAll() {
        return libraryRepository.findAllByOwner_Id(authenticatedUserData.getSpotifyUserId());
    }


    public Library getById(String id) {
        return libraryRepository.findById(id).orElse(null);
    }
}
