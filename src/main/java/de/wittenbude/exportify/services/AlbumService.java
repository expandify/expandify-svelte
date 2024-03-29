package de.wittenbude.exportify.services;

import de.wittenbude.exportify.exceptions.EntityNotFoundException;
import de.wittenbude.exportify.models.Album;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Snapshot;
import de.wittenbude.exportify.models.Track;
import de.wittenbude.exportify.models.converter.AlbumConverter;
import de.wittenbude.exportify.repositories.AlbumRepository;
import de.wittenbude.exportify.repositories.SnapshotRepository;
import de.wittenbude.exportify.request.CurrentUser;
import de.wittenbude.exportify.spotify.clients.SpotifyAlbumsClient;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class AlbumService {

    private final SpotifyAlbumsClient spotifyAlbumsClient;
    private final AlbumRepository albumRepository;
    private final SnapshotRepository snapshotRepository;
    private final CurrentUser currentUser;
    private final ArtistService artistService;
    private final TrackService trackService;

    public AlbumService(SpotifyAlbumsClient spotifyAlbumsClient,
                        AlbumRepository albumRepository,
                        SnapshotRepository snapshotRepository,
                        CurrentUser currentUser,
                        ArtistService artistService,
                        TrackService trackService) {
        this.spotifyAlbumsClient = spotifyAlbumsClient;
        this.albumRepository = albumRepository;
        this.snapshotRepository = snapshotRepository;
        this.currentUser = currentUser;
        this.artistService = artistService;
        this.trackService = trackService;
    }


    public Stream<Snapshot.SavedAlbum> loadSavedAlbums() {
        return SpotifyPage
                .streamPagination(offset -> spotifyAlbumsClient.getSaved(50, offset))
                .map(spotifySavedAlbum -> {
                    List<String> artistIDs = artistService
                            .loadArtists(spotifySavedAlbum.getAlbum().getArtists())
                            .map(Artist::getSpotifyID)
                            .toList();

                    List<String> trackIDs = trackService
                            .loadAlbumTracks(spotifySavedAlbum.getAlbum().getId())
                            .map(Track::getSpotifyID)
                            .toList();

                    Album album = AlbumConverter.from(
                            spotifySavedAlbum.getAlbum(),
                            artistIDs,
                            trackIDs);
                    Album savedAlbum = albumRepository.upsert(album);

                    return new Snapshot.SavedAlbum()
                            .setSavedAt(spotifySavedAlbum.getAddedAt().toInstant())
                            .setAlbum(savedAlbum);
                });
    }


    public Snapshot.SavedAlbum get(UUID snapshot, String spotifyAlbumIDs) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return albumRepository
                .getFromSnapshot(snapshot, spotifyAlbumIDs)
                .orElseThrow(() -> new EntityNotFoundException("artist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyAlbumIDs)));
    }

    public Stream<Snapshot.SavedAlbum> get(UUID snapshot) {
        snapshotRepository.findById(snapshot)
                .filter(s -> s.getExportifyUser().getId().equals(currentUser.getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(snapshot)));

        return albumRepository
                .getFromSnapshot(snapshot)
                .stream();
    }

}
