package dev.kenowi.exportify.domain.service.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class SnapshotArtistService {

    private final ArtistRepository artistRepository;

    SnapshotArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    public Artist get(UUID snapshot, String spotifyArtistID) {
        return artistRepository
                .getFromSnapshot(snapshot, spotifyArtistID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyArtistID)));
    }

    public Stream<Artist> get(UUID snapshot) {
        return artistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }
}
