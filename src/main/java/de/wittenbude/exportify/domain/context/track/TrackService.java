package de.wittenbude.exportify.domain.context.track;

import de.wittenbude.exportify.domain.entities.Track;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.events.ArtistsLoadedEvent;
import de.wittenbude.exportify.domain.events.PlaylistTrackLoadedEvent;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.domain.valueobjects.SavedTrack;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyTrackClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTrack;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyTrackMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyTrackMapper spotifyTrackMapper;

    public TrackService(TrackRepository trackRepository,
                        SpotifyTrackClient spotifyTrackClient,
                        ApplicationEventPublisher eventBus,
                        SpotifyTrackMapper spotifyTrackMapper) {
        this.trackRepository = trackRepository;
        this.spotifyTrackClient = spotifyTrackClient;
        this.eventBus = eventBus;
        this.spotifyTrackMapper = spotifyTrackMapper;
    }

    public Set<SavedTrack> loadSavedTracks() {
        return Set.of();
    }

    public SavedTrack get(UUID snapshot, String spotifyTrackID) {
        return trackRepository
                .getFromSnapshot(snapshot, spotifyTrackID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyTrackID)));
    }

    public Stream<SavedTrack> get(UUID snapshot) {
        return trackRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

    @EventListener
    @Async
    public void loadAlbumTracks(AlbumLoadedEvent event) {
        event.getAlbum()
                .getSpotifyTrackIDs()
                .stream()
                .map(spotifyTrackClient::get)
                .peek(spotifyTrack -> eventBus.publishEvent(new ArtistsLoadedEvent(spotifyTrack.getArtists())))
                .map(spotifyTrackMapper::toEntity)
                .forEach(trackRepository::upsert);
    }

    @EventListener
    @Async
    public void loadAlbumTracks(PlaylistTrackLoadedEvent event) {
        if (!(event.getTrack() instanceof SpotifyTrack spotifyTrack)) {
            return;
        }
        Track track = spotifyTrackMapper.toEntity(spotifyTrack);
        // TODO save Album
        // TODO save Artists
        trackRepository.upsert(track);
    }
}
