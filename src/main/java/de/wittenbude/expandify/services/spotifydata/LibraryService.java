package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.SpotifyUserPrivate;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class LibraryService {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryService.class);
    private final PersistenceService persistenceService;
    private final AuthenticatedUserData authenticatedUserData;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final SpotifyUserService spotifyUserService;

    public LibraryService(
            PersistenceService persistenceService,
            AuthenticatedUserData authenticatedUserData,
            AlbumService albumService,
            ArtistService artistService,
            PlaylistService playlistService,
            TrackService trackService,
            SpotifyUserService spotifyUserService
    ) {
        this.persistenceService = persistenceService;
        this.authenticatedUserData = authenticatedUserData;
        this.albumService = albumService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.spotifyUserService = spotifyUserService;
    }

    public Library createLibrary() throws SpotifyWebApiException {
        List<SavedAlbum> albums = albumService
                .getSaved()
                .stream()
                .peek(a -> a.getAlbum().setTracks(albumService.getTracks(a.getAlbum().getId())))
                .toList();
        LOG.debug("Albums Loaded: {}", albums.size());

        List<PlaylistSimplified> playlists = playlistService
                .getSaved()
                .stream()
                .peek(p -> p.setTracks(playlistService.getTracks(p.getSpotifyId())))
                .toList();
        LOG.debug("Playlists Loaded: {}", playlists.size());

        List<Artist> artists = artistService.getFollowed();
        LOG.debug("Artists Loaded: {}", artists.size());

        List<SavedTrack> tracks = trackService.getLatest();
        LOG.debug("Tracks Loaded: {}", tracks.size());

        SpotifyUserPrivate owner = spotifyUserService.getCurrent();
        LOG.debug("Owner Loaded: {}", owner.getDisplayName());

        Library library = new Library(
                albums,
                tracks,
                artists,
                playlists,
                owner,
                Date.from(Instant.now())
        );

        Library saved = persistenceService.save(library);
        LOG.info("New Library Saved: {}", library.getId());
        return saved;
    }

    public List<Library> getAll() {
        return persistenceService.findLibraries(authenticatedUserData.getSpotifyUserId());
    }


    public Library getById(String id) {
        return persistenceService.findLibrary(id).orElse(null);
    }
}
