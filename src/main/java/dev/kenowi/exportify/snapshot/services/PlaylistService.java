package dev.kenowi.exportify.snapshot.services;

import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.snapshot.entities.Playlist;
import dev.kenowi.exportify.snapshot.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.snapshot.mappers.SpotifyPlaylistMapper;
import dev.kenowi.exportify.snapshot.repositories.PlaylistRepository;
import dev.kenowi.exportify.spotify.clients.SpotifyPlaylistClient;
import dev.kenowi.exportify.spotify.dao.SpotifyIdProjection;
import dev.kenowi.exportify.spotify.dao.SpotifyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class PlaylistService {

    private final SpotifyPlaylistClient spotifyPlaylistClient;
    private final PlaylistRepository playlistRepository;
    private final SpotifyPlaylistMapper spotifyPlaylistMapper;

    PlaylistService(SpotifyPlaylistClient spotifyPlaylistClient,
                    PlaylistRepository playlistRepository,
                    SpotifyPlaylistMapper spotifyPlaylistMapper) {
        this.spotifyPlaylistClient = spotifyPlaylistClient;
        this.playlistRepository = playlistRepository;
        this.spotifyPlaylistMapper = spotifyPlaylistMapper;
    }

    public Stream<Playlist> loadUserPlaylists() {
        return SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getUserPlaylistIDs(50, offset))
                .map(SpotifyIdProjection::getId)
                .map(spotifyPlaylistClient::get)
                .map(spotifyPlaylistMapper::toEntity)
                .map(this::loadPlaylistTracks)
                .map(playlistRepository::upsert);
    }

    public Playlist get(UUID snapshot, String spotifyPlaylistID) {
        return playlistRepository
                .getFromSnapshot(snapshot, spotifyPlaylistID)
                .orElseThrow(() -> new EntityNotFoundException("playlist %s does not exist in snapshot %s"
                        .formatted(snapshot, spotifyPlaylistID)));
    }

    public Stream<Playlist> get(UUID snapshot) {
        return playlistRepository
                .getFromSnapshot(snapshot)
                .stream();
    }


    private Playlist loadPlaylistTracks(Playlist playlist) {
        List<PlaylistTrack> tracks = SpotifyPage
                .streamPagination(offset -> spotifyPlaylistClient.getPlaylistItemIDs(playlist.getSpotifyID(), 50, offset))
                .map(spotifyPlaylistMapper::map)
                .toList();
        return playlist.setTracks(tracks);
    }

}
