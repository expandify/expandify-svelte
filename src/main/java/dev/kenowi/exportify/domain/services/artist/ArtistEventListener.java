package dev.kenowi.exportify.domain.services.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.events.ArtistIDsLoaded;
import dev.kenowi.exportify.domain.events.SnapshotCreatedEvent;
import dev.kenowi.exportify.domain.utils.StreamHelper;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyArtistClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyCursorPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyArtistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArtistEventListener {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;
    private final SpotifyArtistMapper spotifyArtistMapper;
    private final ApplicationEventPublisher eventPublisher;

    ArtistEventListener(SpotifyArtistClient spotifyArtistClient,
                        ArtistRepository artistRepository,
                        SpotifyArtistMapper spotifyArtistMapper,
                        ApplicationEventPublisher eventPublisher) {
        this.spotifyArtistClient = spotifyArtistClient;
        this.artistRepository = artistRepository;
        this.spotifyArtistMapper = spotifyArtistMapper;
        this.eventPublisher = eventPublisher;
    }


    @Async
    @EventListener
    public void loadFollowedArtists(SnapshotCreatedEvent event) {
        Set<Artist> artists = SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .collect(Collectors.toSet());

        eventPublisher.publishEvent(event.artistsCreated(this, artists));
    }

    @Async
    @EventListener
    public void loadArtistIDs(ArtistIDsLoaded event) {
        event.getArtistsIDs()
                .stream()
                .distinct()
                .collect(StreamHelper.chunked(50))
                .map(ids -> String.join(",", ids))
                .map(spotifyArtistClient::getArtists)
                .map(response -> response.get("artists"))
                .flatMap(List::stream)
                .map(spotifyArtistMapper::toEntity)
                .forEach(artistRepository::upsert);
    }
}
