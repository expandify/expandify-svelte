package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Snapshot;
import de.wittenbude.exportify.repositories.SnapshotRepository;
import de.wittenbude.exportify.request.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SnapshotService {

    private final ArtistService artistService;
    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;

    public SnapshotService(ArtistService artistService,
                           SnapshotRepository snapshotRepository,
                           CurrentUser currentUser) {
        this.artistService = artistService;
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
    }


    public Snapshot create() {

        Set<Artist> artists = artistService.loadFollowedArtists();

        return snapshotRepository.save(new Snapshot()
                .setArtists(artists)
                .setExportifyUser(currentUser.getUser()));
    }
}
