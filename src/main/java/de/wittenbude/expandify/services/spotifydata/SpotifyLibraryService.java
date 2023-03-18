package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyArtist;
import de.wittenbude.expandify.models.spotifydata.SpotifyLibrary;
import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SpotifySavedTrack;
import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.requestscope.CurrentUser;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@Service
public class SpotifyLibraryService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final LibraryRepository libraryRepository;
    private final CurrentUser currentUser;
    private final SpotifyUserService userService;

    public SpotifyLibraryService(
            LibraryRepository libraryRepository,
            CurrentUser currentUser,
            SpotifyUserService userService
    ) {
        this.libraryRepository = libraryRepository;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    public List<SpotifySavedAlbum> saveAlbumsToLatest(List<SpotifySavedAlbum> albums) throws SpotifyWebApiException {
        SpotifyLibrary libraryLatest = getLatestOrCreate();

        libraryLatest.setSavedAlbums(albums);
        return libraryRepository.save(libraryLatest).getSavedAlbums();
    }

    public List<SpotifyArtist> saveArtistsToLatest(List<SpotifyArtist> artists) throws SpotifyWebApiException {
        SpotifyLibrary libraryLatest = getLatestOrCreate();

        libraryLatest.setFollowedArtists(artists);
        return libraryRepository.save(libraryLatest).getFollowedArtists();
    }

    public List<SpotifyPlaylistSimplified> savePlaylistToLatest(List<SpotifyPlaylistSimplified> playlists) throws SpotifyWebApiException {
        SpotifyLibrary libraryLatest = getLatestOrCreate();

        libraryLatest.setPlaylists(playlists);
        return libraryRepository.save(libraryLatest).getPlaylists();
    }

    public List<SpotifySavedTrack> saveTracksToLatest(List<SpotifySavedTrack> tracks) throws SpotifyWebApiException {
        SpotifyLibrary libraryLatest = getLatestOrCreate();

        libraryLatest.setSavedTracks(tracks);
        return libraryRepository.save(libraryLatest).getSavedTracks();
    }

    private SpotifyLibrary getLatestOrCreate() throws SpotifyWebApiException {
        return libraryRepository
                .findAllByOwner_IdAndLatestTrue(currentUser.getSpotifyUserId())
                .orElse(new SpotifyLibrary(true, null, userService.getOrLoadCurrent()));
    }


}
