package de.wittenbude.expandify.services.spotify;

import de.wittenbude.expandify.models.db.*;
import de.wittenbude.expandify.models.pojos.SavedAlbum;
import de.wittenbude.expandify.models.pojos.SavedTrack;
import de.wittenbude.expandify.repositories.libraries.LibraryDao;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class LibraryService {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryService.class);
    private final LibraryDao libraryDao;
    private final AuthenticatedUserService authenticatedUserService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final SpotifyUserService spotifyUserService;

    public LibraryService(
            LibraryDao libraryDao,
            AuthenticatedUserService authenticatedUserService,
            AlbumService albumService,
            ArtistService artistService,
            PlaylistService playlistService,
            TrackService trackService,
            SpotifyUserService spotifyUserService
    ) {
        this.libraryDao = libraryDao;
        this.authenticatedUserService = authenticatedUserService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.spotifyUserService = spotifyUserService;
    }

    public Library createLibrary() throws SpotifyWebApiException {
        List<SavedAlbum> albums = albumService
                .getSaved(authenticatedUserService.getUserId())
                .stream()
                .peek(a -> a.getAlbum().setTracks(albumService.getTracks(a.getAlbum().getId())))
                .toList();
        LOG.debug("Albums Loaded: {}", albums.size());

        List<Playlist> playlists = playlistService
                .getSaved(authenticatedUserService.getUserId())
                .stream()
                .map(PlaylistInfo::getId)
                .map(playlistService::get)
                .toList();
        LOG.debug("Playlists Loaded: {}", playlists.size());

        List<Artist> artists = artistService.getFollowed(authenticatedUserService.getUserId());
        LOG.debug("Artists Loaded: {}", artists.size());

        List<SavedTrack> tracks = trackService.getLatest(authenticatedUserService.getUserId());
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

        Library saved = libraryDao.save(library);
        LOG.info("New Library Saved: {}", library.getId());
        return saved;
    }

    public Collection<Library> getAll() {
        return libraryDao.findAll(authenticatedUserService.getUserId());
    }


    public Library getById(String id) {
        return libraryDao.find(id).orElse(null);
    }
}
