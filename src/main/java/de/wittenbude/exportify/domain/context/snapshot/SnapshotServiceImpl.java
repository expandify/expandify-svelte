package de.wittenbude.exportify.domain.context.snapshot;

import de.wittenbude.exportify.domain.context.album.AlbumService;
import de.wittenbude.exportify.domain.context.artist.ArtistService;
import de.wittenbude.exportify.domain.context.exportifyuser.CurrentUser;
import de.wittenbude.exportify.domain.context.track.TrackService;
import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.entities.SavedAlbum;
import de.wittenbude.exportify.domain.entities.SavedTrack;
import de.wittenbude.exportify.domain.entities.Snapshot;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final TrackService trackService;

    public SnapshotServiceImpl(SnapshotRepository snapshotRepository,
                               CurrentUser currentUser,
                               ArtistService artistService,
                               AlbumService albumService,
                               TrackService trackService) {
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
        this.artistService = artistService;
        this.albumService = albumService;
        this.trackService = trackService;
    }


    @Override
    public Snapshot create() {

        Set<Artist> artists = artistService.loadFollowedArtists();
        Set<SavedAlbum> albums = albumService.loadSavedAlbums();
        Set<SavedTrack> tracks = trackService.loadSavedTracks();

        return snapshotRepository.save(new Snapshot()
                .setExportifyUser(currentUser.getUser())
                .setArtists(artists)
                .setSavedTracks(tracks)
                .setSavedAlbums(albums));
    }

    @Override
    public Snapshot get(UUID id) {
        return snapshotRepository
                .findById(id)
                .filter(snapshot -> snapshot.getExportifyUser().getId() == currentUser.getID())
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));
    }
}
