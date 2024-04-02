package de.wittenbude.exportify.domain.context.track;

import de.wittenbude.exportify.domain.entities.SavedTrack;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.events.ArtistsLoadedEvent;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyTrackClient;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyTrackMapper;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyTrackMapper spotifyTrackMapper;

    public TrackServiceImpl(TrackRepository trackRepository,
                            SpotifyTrackClient spotifyTrackClient,
                            ApplicationEventPublisher eventBus,
                            SpotifyTrackMapper spotifyTrackMapper) {
        this.trackRepository = trackRepository;
        this.spotifyTrackClient = spotifyTrackClient;
        this.eventBus = eventBus;
        this.spotifyTrackMapper = spotifyTrackMapper;
    }

    @Override
    public Set<SavedTrack> loadSavedTracks() {
        return Set.of();
    }

    @Override
    public SavedTrack get(UUID snapshot, String spotifyTrackID) {
        return trackRepository
                .getFromSnapshot(snapshot, spotifyTrackID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyTrackID)));
    }

    @Override
    public Stream<SavedTrack> get(UUID snapshot) {
        return trackRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

    @EventListener
    @Async
    @Override
    public void loadAlbumTracks(AlbumLoadedEvent event) {
        event.getAlbum()
                .getSpotifyTrackIDs()
                .stream()
                .map(spotifyTrackClient::get)
                .peek(spotifyTrack -> eventBus.publishEvent(new ArtistsLoadedEvent(spotifyTrack.getArtists())))
                .map(spotifyTrackMapper::toEntity)
                .forEach(trackRepository::upsert);

    }
}
