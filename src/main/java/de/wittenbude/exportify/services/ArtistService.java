package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.converter.ArtistConverter;
import de.wittenbude.exportify.repositories.ArtistRepository;
import de.wittenbude.exportify.repositories.SnapshotRepository;
import de.wittenbude.exportify.request.CurrentUser;
import de.wittenbude.exportify.spotify.clients.SpotifyArtistsClient;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArtistService {

    private final SpotifyArtistsClient spotifyArtistsClient;
    private final ArtistRepository artistRepository;
    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;

    public ArtistService(SpotifyArtistsClient spotifyArtistsClient,
                         ArtistRepository artistRepository,
                         SnapshotRepository snapshotRepository,
                         CurrentUser currentUser) {
        this.spotifyArtistsClient = spotifyArtistsClient;
        this.artistRepository = artistRepository;
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
    }


    public Set<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistsClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(ArtistConverter::from)
                .map(artistRepository::upsert)
                .collect(Collectors.toSet());
    }


    public Artist get(UUID snapshot, String spotifyArtistID) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return artistRepository
                .getArtistFromSnapshot(snapshot, spotifyArtistID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyArtistID)));
    }

    public Stream<Artist> get(UUID snapshot) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return artistRepository
                .getArtistsFromSnapshot(snapshot)
                .stream();
    }
}
