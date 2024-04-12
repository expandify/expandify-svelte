package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.authentication.AuthenticatedUser;
import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.shared.exceptions.SnapshotNotReadyException;
import dev.kenowi.exportify.snapshot.entities.*;
import dev.kenowi.exportify.snapshot.entities.valueobjects.*;
import dev.kenowi.exportify.snapshot.repositories.SnapshotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final SpotifyUserService spotifyUserService;

    SnapshotService(SnapshotRepository snapshotRepository,
                    AlbumService albumService,
                    ArtistService artistService,
                    PlaylistService playlistService,
                    TrackService trackService,
                    SpotifyUserService spotifyUserService) {
        this.snapshotRepository = snapshotRepository;
        this.albumService = albumService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.spotifyUserService = spotifyUserService;
    }


    public Snapshot create() {

        Snapshot snap = snapshotRepository
                .save(new Snapshot().setExportifyUser(AuthenticatedUser.getSecurityContext().getUser()));

        //CompletableFuture.runAsync(() -> this.fillSnapshot(snap.getId()));

        this.fillSnapshot(snap.getId());
        return snap;
    }

    public Snapshot get(UUID id) {
        Snapshot snapshot = snapshotRepository
                .findById(id)
                .filter(s -> s.getExportifyUser().getId().equals(AuthenticatedUser.getSecurityContext().getID()))
                .orElseThrow(() -> new EntityNotFoundException("snapshot %s does not exist".formatted(id)));

        if (snapshot.getSnapshotStatus() == EventStatus.PENDING) {
            throw new SnapshotNotReadyException("Snapshot not ready");
        }
        return snapshot;
    }


    private void fillSnapshot(UUID snapshotID) {

        PrivateSpotifyUser privateUser = spotifyUserService.loadCurrentSpotifyUser();
        Set<Artist> artists = artistService.loadFollowedArtists().collect(Collectors.toSet());


        Set<SavedAlbum> savedAlbums = albumService.loadSavedAlbums().collect(Collectors.toSet());
        List<String> albumTrackIDs = savedAlbums.stream().map(SavedAlbum::getAlbum).map(Album::getSpotifyTrackIDs).flatMap(List::stream).toList();
        List<String> albumArtistIDs = savedAlbums.stream().map(SavedAlbum::getAlbum).map(Album::getSpotifyArtistIDs).flatMap(List::stream).toList();


        Set<SavedTrack> savedTracks = trackService.loadSavedTracks().collect(Collectors.toSet());
        List<String> trackAlbumIDs = savedTracks.stream().map(SavedTrack::getTrack).map(Track::getSpotifyAlbumID).toList();
        List<String> trackArtistIDs = savedTracks.stream().map(SavedTrack::getTrack).map(Track::getSpotifyArtistIDs).flatMap(List::stream).toList();


        Set<Playlist> playlists = playlistService.loadUserPlaylists().collect(Collectors.toSet());
        List<PlaylistTrack> playlistTracks = playlists.stream().map(Playlist::getTracks).flatMap(List::stream).toList();
        List<String> playlistTrackIDs = playlistTracks.stream().filter(playlist -> SpotifyObjectType.TRACK.equals(playlist.getSpotifyObjectType())).map(PlaylistTrack::getSpotifyTrackID).toList();
        List<String> playlistEpisodeIDs = playlistTracks.stream().filter(playlist -> SpotifyObjectType.EPISODE.equals(playlist.getSpotifyObjectType())).map(PlaylistTrack::getSpotifyTrackID).toList();


        List<String> allNestedTracksIDs = Stream.of(albumTrackIDs, playlistTrackIDs).flatMap(List::stream).distinct().toList();
        List<Track> allNestedTracks = trackService.loadTrackIDs(allNestedTracksIDs);
        List<String> nestedTracksAlbumIDs = allNestedTracks.stream().map(Track::getSpotifyAlbumID).toList();
        List<String> nestedTracksArtistIDs = allNestedTracks.stream().map(Track::getSpotifyArtistIDs).flatMap(List::stream).toList();


        List<String> allNestedAlbumIDs = Stream.of(trackAlbumIDs, nestedTracksAlbumIDs).flatMap(List::stream).distinct().toList();
        List<Album> allNestedAlbums = albumService.loadAlbumIDs(allNestedAlbumIDs);
        List<String> nestedAlbumsArtistIDs = allNestedAlbums.stream().map(Album::getSpotifyArtistIDs).flatMap(List::stream).toList();


        List<String> allNestedArtistsIDs = Stream.of(albumArtistIDs, trackArtistIDs, nestedTracksArtistIDs, nestedAlbumsArtistIDs).flatMap(List::stream).toList();
        List<Artist> allNestedArtists = artistService.loadArtistIDs(allNestedArtistsIDs);


        Snapshot snapshot = snapshotRepository
                .findById(snapshotID)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

        snapshot.setPrivateSpotifyUser(privateUser);
        snapshot.setArtists(artists);
        snapshot.setSavedAlbums(savedAlbums);
        snapshot.setSavedTracks(savedTracks);
        snapshot.setPlaylists(playlists);

        snapshotRepository.save(snapshot);
    }
}
