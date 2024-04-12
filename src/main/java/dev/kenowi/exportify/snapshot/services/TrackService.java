package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.shared.utils.StreamHelper;
import dev.kenowi.exportify.snapshot.entities.Track;
import dev.kenowi.exportify.snapshot.entities.valueobjects.SavedTrack;
import dev.kenowi.exportify.snapshot.mappers.SpotifyTrackMapper;
import dev.kenowi.exportify.snapshot.repositories.TrackRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyTrackClient;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyTrackClient;
    private final SpotifyTrackMapper spotifyTrackMapper;

    TrackService(TrackRepository trackRepository,
                 SpotifyTrackClient spotifyTrackClient,
                 SpotifyTrackMapper spotifyTrackMapper) {
        this.trackRepository = trackRepository;
        this.spotifyTrackClient = spotifyTrackClient;
        this.spotifyTrackMapper = spotifyTrackMapper;
    }

    public Stream<SavedTrack> loadSavedTracks() {
        return SpotifyPage
                .streamPagination(offset -> spotifyTrackClient.getSaved(50, offset))
                .map(spotifyTrackMapper::toEntity)
                .map(savedTrack -> savedTrack.setTrack(trackRepository.upsert(savedTrack.getTrack())));
    }

    public List<Track> loadTrackIDs(Collection<String> trackIDs) {
        return trackIDs
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
}
