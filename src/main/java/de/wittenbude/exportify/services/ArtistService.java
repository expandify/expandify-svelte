package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.converter.ArtistConverter;
import de.wittenbude.exportify.repositories.ArtistRepository;
import de.wittenbude.exportify.repositories.SnapshotRepository;
import de.wittenbude.exportify.request.CurrentUser;
import de.wittenbude.exportify.spotify.clients.SpotifyArtistsClient;
import de.wittenbude.exportify.spotify.data.SpotifyArtistSimplified;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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


    public Stream<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistsClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(ArtistConverter::from)
                .map(artistRepository::upsert);
    }

    public Stream<Artist> loadArtists(SpotifyArtistSimplified[] artists) {
        return loadArtists(Arrays.stream(artists).map(SpotifyArtistSimplified::getId).toList());
    }

    public Stream<Artist> loadArtists(List<String> spotifyIDs) {
        return spotifyArtistsClient
                .getArtists(String.join(",", spotifyIDs))
                .get("artists")
                .stream()
                .map(ArtistConverter::from)
                .map(artistRepository::upsert);
    }


    public Artist get(UUID snapshot, String spotifyArtistID) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return artistRepository
                .getFromSnapshot(snapshot, spotifyArtistID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyArtistID)));
    }

    public Stream<Artist> get(UUID snapshot) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return artistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }
}
