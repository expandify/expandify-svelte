package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Snapshot;
import de.wittenbude.exportify.repositories.SnapshotRepository;
import de.wittenbude.exportify.request.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SnapshotService {

    private final ArtistService artistService;
    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;
    private final AlbumService albumService;

    public SnapshotService(ArtistService artistService,
                           SnapshotRepository snapshotRepository,
                           CurrentUser currentUser, AlbumService albumService) {
        this.artistService = artistService;
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
        this.albumService = albumService;
    }


    public Snapshot create() {

        Set<Artist> artists = artistService.loadFollowedArtists().collect(Collectors.toSet());
        Set<Snapshot.SavedAlbum> albums = albumService.loadSavedAlbums().collect(Collectors.toSet());

        //Set<Track> tracks = trackService.loadSavedTracks();

        return snapshotRepository.save(new Snapshot()
                .setArtists(artists)
                .setExportifyUser(currentUser.getUser())
                .setSavedAlbums(albums));
    }
}
