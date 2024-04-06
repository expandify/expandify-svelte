package dev.kenowi.exportify.domain.services.artist;

import dev.kenowi.exportify.domain.entities.Album;
import dev.kenowi.exportify.domain.entities.Artist;
import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.events.*;
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
    public void loadFollowedArtists(SnapshotEvent.Created event) {
        Set<Artist> artists = SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(spotifyArtistMapper::toEntity)
                .map(artistRepository::upsert)
                .collect(Collectors.toSet());

        eventPublisher.publishEvent(new SnapshotEvent.ArtistsCreated(this, event.getSnapshot(), artists));
    }

    @Async
    @EventListener
    public void loadAlbumArtists(AlbumsCreatedEvent event) {

        Stream<String> artistIds = event
                .getAlbums()
                .stream()
                .map(Album::getSpotifyArtistIDs)
                .flatMap(List::stream);
        List<Artist> artists = loadArtistsByID(artistIds);

        eventPublisher.publishEvent(new ArtistsCreatedEvent(this, artists));
    }

    @Async
    @EventListener
    public void loadTrackArtists(TracksCreatedEvent event) {
        Stream<String> spotifyArtistIDs = event
                .getTracks()
                .stream()
                .map(Track::getSpotifyArtistIDs)
                .flatMap(List::stream);
        List<Artist> artists = loadArtistsByID(spotifyArtistIDs);
        eventPublisher.publishEvent(new TracksEvent.ArtistsCreated(this, event.getTracks(), artists));
    }

    private List<Artist> loadArtistsByID(Stream<String> spotifyArtistIDs) {
        return spotifyArtistIDs
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
}
