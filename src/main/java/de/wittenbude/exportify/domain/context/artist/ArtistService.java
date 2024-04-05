package de.wittenbude.exportify.domain.context.artist;

import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.events.ArtistsLoadedEvent;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyArtistClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyCursorPage;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyArtistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ArtistService {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;
    private final SpotifyArtistMapper spotifyArtistMapper;

    public ArtistService(SpotifyArtistClient spotifyArtistClient,
                         ArtistRepository artistRepository,
                         SpotifyArtistMapper spotifyArtistMapper) {
        this.spotifyArtistClient = spotifyArtistClient;
        this.artistRepository = artistRepository;
        this.spotifyArtistMapper = spotifyArtistMapper;
    }


    public Set<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .peek(artist -> log.info("Loaded artist '{}'", artist.getName()))
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .collect(Collectors.toSet());
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

    @EventListener
    @Async
    public void loadArtists(AlbumLoadedEvent event) {
        String artistIDs = String.join(",", event.getAlbum().getSpotifyArtistIDs());
        spotifyArtistClient
                .getArtists(artistIDs)
                .get("artists")
                .stream()
                .map(spotifyArtistMapper::toEntity)
                .forEach(artistRepository::upsert);
    }

    @EventListener
    @Async
    public void persistArtists(ArtistsLoadedEvent event) {
        event.getArtists()
                .stream()
                .map(spotifyArtistMapper::toEntity)
                .forEach(artistRepository::upsert);
    }
}
