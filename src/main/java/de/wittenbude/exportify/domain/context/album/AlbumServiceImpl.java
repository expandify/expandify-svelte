package de.wittenbude.exportify.domain.context.album;

import de.wittenbude.exportify.domain.entities.SavedAlbum;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.exception.EntityNotFoundException;
import de.wittenbude.exportify.infrastructure.spotify.clients.SpotifyAlbumClient;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPage;
import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyTrackSimplified;
import de.wittenbude.exportify.infrastructure.spotify.mappers.SpotifyAlbumMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class AlbumServiceImpl implements AlbumService {

    private final SpotifyAlbumClient spotifyAlbumClient;
    private final AlbumRepository albumRepository;
    private final ApplicationEventPublisher eventBus;
    private final SpotifyAlbumMapper spotifyAlbumMapper;

    public AlbumServiceImpl(SpotifyAlbumClient spotifyAlbumClient,
                            AlbumRepository albumRepository,
                            ApplicationEventPublisher eventBus,
                            SpotifyAlbumMapper spotifyAlbumMapper) {
        this.spotifyAlbumClient = spotifyAlbumClient;
        this.albumRepository = albumRepository;
        this.eventBus = eventBus;
        this.spotifyAlbumMapper = spotifyAlbumMapper;
    }


    @Override
    public Set<SavedAlbum> loadSavedAlbums() {
        return SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifyAlbumMapper::toEntity)
                .peek(this::loadAlbumTrackIDs)
                .map(savedAlbum -> savedAlbum.setAlbum(albumRepository.upsert(savedAlbum.getAlbum())))
                .peek(savedAlbum -> eventBus.publishEvent(new AlbumLoadedEvent(savedAlbum.getAlbum())))
                .collect(Collectors.toSet());
    }

    @Override
    public SavedAlbum get(UUID snapshot, String spotifyAlbumID) {
        return albumRepository
                .getFromSnapshot(snapshot, spotifyAlbumID)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyAlbumID)));
    }

    @Override
    public Stream<SavedAlbum> get(UUID snapshot) {
        return albumRepository
                .getFromSnapshot(snapshot)
                .stream();
    }


    private void loadAlbumTrackIDs(SavedAlbum savedAlbum) {
        String albumID = savedAlbum.getAlbum().getSpotifyID();
        List<String> trackIDs = SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getAlbumTracks(albumID, 50, offset))
                .map(SpotifyTrackSimplified::getId)
                .toList();
        savedAlbum.getAlbum().setSpotifyTrackIDs(trackIDs);
    }

}
