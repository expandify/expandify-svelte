package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.Snapshot;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import dev.kenowi.exportify.domain.service.album.AlbumService;
import dev.kenowi.exportify.domain.service.artist.ArtistService;
import dev.kenowi.exportify.domain.service.auth.AuthenticatedUser;
import dev.kenowi.exportify.domain.service.playlist.PlaylistService;
import dev.kenowi.exportify.domain.service.track.TrackService;
import dev.kenowi.exportify.domain.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.valueobjects.SavedTrack;
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

    SnapshotService(SnapshotRepository snapshotRepository,
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
