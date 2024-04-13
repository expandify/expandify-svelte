package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.shared.utils.StreamHelper;
import dev.kenowi.exportify.snapshot.entities.Artist;
import dev.kenowi.exportify.snapshot.mappers.SpotifyArtistMapper;
import dev.kenowi.exportify.snapshot.repositories.ArtistRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyArtistClient;
import dev.kenowi.exportify.spotify.dao.SpotifyCursorPage;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class ArtistService {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;
    private final SpotifyArtistMapper spotifyArtistMapper;

    ArtistService(@RestClient SpotifyArtistClient spotifyArtistClient,
                  ArtistRepository artistRepository,
                  SpotifyArtistMapper spotifyArtistMapper) {
        this.spotifyArtistClient = spotifyArtistClient;
        this.artistRepository = artistRepository;
        this.spotifyArtistMapper = spotifyArtistMapper;
    }


    public Stream<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert);
    }

    public List<Artist> loadArtistIDs(List<String> artistIDs) {
        return artistIDs
                .stream()
                .distinct()
                .collect(StreamHelper.chunked(50))
                .map(ids -> String.join(",", ids))
                .map(spotifyArtistClient::getArtists)
                .map(response -> response.get("artists"))
                .flatMap(List::stream)
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .toList();
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
