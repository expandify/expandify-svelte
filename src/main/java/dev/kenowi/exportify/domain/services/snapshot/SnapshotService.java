package dev.kenowi.exportify.domain.services.snapshot;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.PrivateSpotifyUser;
import dev.kenowi.exportify.domain.entities.Snapshot;
import dev.kenowi.exportify.domain.entities.valueobjects.EventStatus;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedAlbum;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.domain.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.domain.exceptions.SnapshotNotReadyException;
import dev.kenowi.exportify.domain.services.album.AlbumEventListener;
import dev.kenowi.exportify.domain.services.artist.ArtistEventListener;
import dev.kenowi.exportify.domain.services.exportifyuser.AuthenticatedUser;
import dev.kenowi.exportify.domain.services.playlist.PlaylistEventListener;
import dev.kenowi.exportify.domain.services.spotifyuser.SpotifyUserEventListener;
import dev.kenowi.exportify.domain.services.track.TrackEventListener;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final AlbumEventListener albumEventListener;
    private final ArtistEventListener artistEventListener;
    private final PlaylistEventListener playlistEventListener;
    private final TrackEventListener trackEventListener;
    private final SpotifyUserEventListener spotifyUserEventListener;

    SnapshotService(SnapshotRepository snapshotRepository,
                    AlbumEventListener albumEventListener,
                    ArtistEventListener artistEventListener,
                    PlaylistEventListener playlistEventListener,
                    TrackEventListener trackEventListener,
                    SpotifyUserEventListener spotifyUserEventListener) {
        this.snapshotRepository = snapshotRepository;
        this.albumEventListener = albumEventListener;
        this.artistEventListener = artistEventListener;
        this.playlistEventListener = playlistEventListener;
        this.trackEventListener = trackEventListener;
        this.spotifyUserEventListener = spotifyUserEventListener;
    }


    public Snapshot create() {

        Snapshot snap = snapshotRepository
                .save(new Snapshot().setExportifyUser(AuthenticatedUser.getSecurityContext().getUser()));

        CompletableFuture<Set<SavedAlbum>> savedAlbums = albumEventListener.loadSavedAlbums();
        CompletableFuture<Set<Artist>> artists = artistEventListener.loadFollowedArtists();
        CompletableFuture<Set<Playlist>> playlists = playlistEventListener.loadUserPlaylists();
        CompletableFuture<PrivateSpotifyUser> privateUser = spotifyUserEventListener.loadCurrentSpotifyUser();
        CompletableFuture<Set<SavedTrack>> savedTracks = trackEventListener.loadSavedTracks();

        CompletableFuture
                .allOf(savedAlbums, artists, playlists, privateUser, savedTracks)
                .whenComplete((unused, ex) -> {
                    Snapshot snapshot = snapshotRepository
                            .findById(snap.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

                    if (ex != null) {
                        snapshot = snapshot
                                .setSnapshotStatus(EventStatus.ERROR)
                                .setErrorMessage(ex.getMessage());
                    } else {
                        snapshot = snapshot
                                .setSavedAlbums(savedAlbums.join())
                                .setArtists(artists.join())
                                .setPlaylists(playlists.join())
                                .setPrivateSpotifyUser(privateUser.join())
                                .setSavedTracks(savedTracks.join())
                                .setSnapshotStatus(EventStatus.FINISHED);
                    }

                    snapshotRepository.save(snapshot);
                });
        return snap;
    }

    public Snapshot get(UUID id) {
        Snapshot snapshot = snapshotRepository
                .findById(id)
                .filter(s -> s.getExportifyUser().getId().equals(AuthenticatedUser.getSecurityContext().getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));

        if (snapshot.getSnapshotStatus() == EventStatus.PENDING) {
            throw new SnapshotNotReadyException("Snapshot not ready");
        }
        return snapshot;
    }
}
