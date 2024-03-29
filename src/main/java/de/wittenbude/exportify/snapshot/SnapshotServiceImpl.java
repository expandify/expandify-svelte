package de.wittenbude.exportify.snapshot;

import de.wittenbude.exportify.album.api.AlbumService;
import de.wittenbude.exportify.album.api.SavedAlbum;
import de.wittenbude.exportify.artist.api.Artist;
import de.wittenbude.exportify.artist.api.ArtistService;
import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.snapshot.api.Snapshot;
import de.wittenbude.exportify.snapshot.api.SnapshotService;
import de.wittenbude.exportify.track.api.SavedTrack;
import de.wittenbude.exportify.track.api.TrackService;
import de.wittenbude.exportify.user.api.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class SnapshotServiceImpl implements SnapshotService {

    private final ArtistService artistService;
    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;
    private final AlbumService albumService;
    private final TrackService trackService;

    public SnapshotServiceImpl(ArtistService artistService,
                               SnapshotRepository snapshotRepository,
                               CurrentUser currentUser,
                               AlbumService albumService,
                               TrackService trackService) {
        this.artistService = artistService;
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
        this.albumService = albumService;
        this.trackService = trackService;
    }


    public Snapshot create() {

        Set<Artist> artists = artistService.loadFollowedArtists().collect(Collectors.toSet());
        Set<SavedAlbum> albums = albumService.loadSavedAlbums().collect(Collectors.toSet());
        Set<SavedTrack> tracks = trackService.loadSavedTracks();

        return snapshotRepository.save(new Snapshot()
                .setExportifyUser(currentUser.getUser())
                .setArtists(artists)
                .setSavedTracks(tracks)
                .setSavedAlbums(albums));
    }

    public void mustExist(UUID snapshot) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));
    }
}
