package dev.kenowi.exportify.domain.services.playlist;

import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class SnapshotPlaylistService {

    private final PlaylistRepository playlistRepository;

    SnapshotPlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }


    public Playlist get(UUID snapshot, String spotifyPlaylistID) {
        return playlistRepository
                .getFromSnapshot(snapshot, spotifyPlaylistID)
                .orElseThrow(() -> new EntityNotFoundException("playlist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyPlaylistID)));
    }

    public Stream<Playlist> get(UUID snapshot) {
        return playlistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

}
