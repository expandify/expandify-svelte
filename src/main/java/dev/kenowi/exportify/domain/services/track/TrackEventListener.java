package dev.kenowi.exportify.domain.services.track;

import dev.kenowi.exportify.domain.entities.Track;
import dev.kenowi.exportify.domain.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.domain.events.AlbumIDsLoaded;
import dev.kenowi.exportify.domain.events.ArtistIDsLoaded;
import dev.kenowi.exportify.domain.events.SnapshotCreatedEvent;
import dev.kenowi.exportify.domain.events.TrackIDsLoaded;
import dev.kenowi.exportify.domain.utils.StreamHelper;
import dev.kenowi.exportify.infrastructure.spotify.clients.SpotifyTrackClient;
import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPage;
import dev.kenowi.exportify.infrastructure.spotify.mappers.SpotifyTrackMapper;
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
public class TrackEventListener {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final SpotifyTrackMapper spotifyTrackMapper;
    private final ApplicationEventPublisher eventPublisher;

    TrackEventListener(TrackRepository trackRepository,
                       SpotifyTrackClient spotifyTrackClient,
                       SpotifyTrackMapper spotifyTrackMapper,
                       ApplicationEventPublisher eventPublisher) {
        this.trackRepository = trackRepository;
        this.spotifyTrackClient = spotifyTrackClient;
        this.eventPublisher = eventPublisher;
        this.spotifyTrackMapper = spotifyTrackMapper;
    }

    @Async
    @EventListener
    public void loadSavedTracks(SnapshotCreatedEvent event) {
        Set<SavedTrack> savedTracks = SpotifyPage
                .streamPagination(offset -> spotifyTrackClient.getSaved(50, offset))
                .map(spotifyTrackMapper::toEntity)
                .map(savedTrack -> savedTrack.setTrack(trackRepository.upsert(savedTrack.getTrack())))
                .collect(Collectors.toSet());


        // TODO make nicer
        List<String> artistIDs = savedTracks
                .stream()
                .map(SavedTrack::getTrack)
                .map(Track::getSpotifyArtistIDs)
                .flatMap(List::stream)
                .toList();
        eventPublisher.publishEvent(new ArtistIDsLoaded(this, artistIDs));

        List<String> albumIDs = savedTracks
                .stream()
                .map(SavedTrack::getTrack)
                .map(Track::getSpotifyAlbumID)
                .toList();
        eventPublisher.publishEvent(new AlbumIDsLoaded(this, albumIDs));

        eventPublisher.publishEvent(event.tracksCreated(this, savedTracks));
    }


    @Async
    @EventListener
    public void loadTrackIDs(TrackIDsLoaded event) {
        List<Track> tracks = event
                .getTrackIDs()
                .stream()
                .distinct()
                .collect(StreamHelper.chunked(50))
                .map(ids -> String.join(",", ids))
                .map(spotifyTrackClient::getTracks)
                .map(response -> response.get("tracks"))
                .flatMap(List::stream)
                .map(spotifyTrackMapper::toEntity)
                .map(trackRepository::upsert)
                .toList();

        List<String> artistIDs = tracks.stream().map(Track::getSpotifyArtistIDs).flatMap(List::stream).toList();
        eventPublisher.publishEvent(new ArtistIDsLoaded(this, artistIDs));

        if (event.getAlbumID() == null) {
            List<String> albumIDs = tracks.stream().map(Track::getSpotifyAlbumID).toList();
            eventPublisher.publishEvent(new AlbumIDsLoaded(this, albumIDs));
        }
    }
}
