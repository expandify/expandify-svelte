package de.wittenbude.exportify.domain.context.artist;

import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.events.ArtistsLoadedEvent;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyArtistClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyCursorPage;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyArtistMapper;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
class ArtistServiceImpl implements ArtistService {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;
    private final SpotifyArtistMapper spotifyArtistMapper;

    public ArtistServiceImpl(SpotifyArtistClient spotifyArtistClient,
                             ArtistRepository artistRepository,
                             SpotifyArtistMapper spotifyArtistMapper) {
        this.spotifyArtistClient = spotifyArtistClient;
        this.artistRepository = artistRepository;
        this.spotifyArtistMapper = spotifyArtistMapper;
    }


    @Override
    public Set<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .collect(Collectors.toSet());
    }

    @Override
    public Artist get(UUID snapshot, String spotifyArtistID) {
        return artistRepository
                .getFromSnapshot(snapshot, spotifyArtistID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyArtistID)));
    }

    @Override
    public Stream<Artist> get(UUID snapshot) {
        return artistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

    @EventListener
    @Async
    @Override
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
    @Override
    public void persistArtists(ArtistsLoadedEvent event) {
        event.getArtists()
                .stream()
                .map(spotifyArtistMapper::toEntity)
                .peek(artist -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(2));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(artist ->  log.info("Artist loaded {}", artist.getName()))
                .forEach(artistRepository::upsert);
    }
}
