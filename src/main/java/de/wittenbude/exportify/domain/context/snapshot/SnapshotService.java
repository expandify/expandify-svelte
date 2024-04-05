package de.wittenbude.exportify.domain.context.snapshot;

import de.wittenbude.exportify.domain.context.album.AlbumService;
import de.wittenbude.exportify.domain.context.artist.ArtistService;
import de.wittenbude.exportify.domain.context.auth.AuthenticatedUser;
import de.wittenbude.exportify.domain.context.playlist.PlaylistService;
import de.wittenbude.exportify.domain.context.track.TrackService;
import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.entities.Playlist;
import de.wittenbude.exportify.domain.entities.Snapshot;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.domain.valueobjects.SavedAlbum;
import de.wittenbude.exportify.domain.valueobjects.SavedTrack;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final TrackService trackService;
    private final PlaylistService playlistService;

    public SnapshotService(SnapshotRepository snapshotRepository,
                           ArtistService artistService,
                           AlbumService albumService,
                           TrackService trackService,
                           PlaylistService playlistService) {
        this.snapshotRepository = snapshotRepository;
        this.artistService = artistService;
        this.albumService = albumService;
        this.trackService = trackService;
        this.playlistService = playlistService;
    }


    public Snapshot create() {

        Set<Artist> artists = artistService.loadFollowedArtists();
        Set<SavedAlbum> albums = albumService.loadSavedAlbums();
        Set<SavedTrack> tracks = trackService.loadSavedTracks();
        Set<Playlist> playlists = playlistService.loadUserPlaylists();

        Snapshot snapshot = new Snapshot()
                .setExportifyUser(AuthenticatedUser.getSecurityContext().getUser())
                .setArtists(artists)
                .setSavedTracks(tracks)
                .setSavedAlbums(albums)
                .setPlaylists(playlists);

        return snapshotRepository.save(snapshot);
    }

    public Snapshot get(UUID id) {
        return snapshotRepository
                .findById(id)
                .filter(snapshot -> snapshot.getExportifyUser().getId() == AuthenticatedUser.getSecurityContext().getID())
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));
    }
}
