package dev.kenowi.exportify.domain.service.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.service.album.AlbumCreatedEvent;
import dev.kenowi.exportify.domain.service.snapshot.SnapshotCreatedEvent;
import dev.kenowi.exportify.domain.service.track.TracksCreatedEvent;
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
import java.util.stream.Stream;

@Service
@Slf4j
class ArtistEventService {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;
    private final SpotifyArtistMapper spotifyArtistMapper;
    private final ApplicationEventPublisher eventPublisher;

    ArtistEventService(SpotifyArtistClient spotifyArtistClient,
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

        artists.stream()
                .map(ArtistCreatedEvent::new)
                .forEach(eventPublisher::publishEvent);

        eventPublisher.publishEvent(event.dataCreated(this, artists));
    }

    @Async
    @EventListener
    public void loadAlbumArtists(AlbumCreatedEvent event) {
        loadArtistsByID(event.getAlbum().getSpotifyArtistIDs().stream());
    }

    @Async
    @EventListener
    public void loadTrackArtists(TracksCreatedEvent event) {
        Stream<String> spotifyArtistIDs = event
                .getTracks()
                .stream()
                .map(TracksCreatedEvent.TrackData::track)
                .map(Track::getSpotifyArtistIDs)
                .flatMap(List::stream);
        loadArtistsByID(spotifyArtistIDs);
    }

    private void loadArtistsByID(Stream<String> spotifyArtistIDs) {
        spotifyArtistIDs
                .distinct()
                .collect(StreamHelper.chunked(100))
                .map(ids -> String.join(",", ids))
                .map(spotifyArtistClient::getArtists)
                .map(response -> response.get("artists"))
                .flatMap(List::stream)
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .map(ArtistCreatedEvent::new)
                .forEach(eventPublisher::publishEvent);
    }
}
