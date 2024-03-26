package de.wittenbude.exportify.services;

import de.wittenbude.exportify.repositories.TrackRepository;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.spotify.clients.SpotifyTracksClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTracksClient spotifyUserClient;

    public TrackService(TrackRepository trackRepository,
                        SpotifyTracksClient spotifyUserClient) {
        this.trackRepository = trackRepository;
        this.spotifyUserClient = spotifyUserClient;
    }

    public List<Artist> loadSavedTracks() {
        return List.of();
    }
}
