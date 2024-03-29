package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.Track;
import de.wittenbude.exportify.models.converter.TrackConverter;
import de.wittenbude.exportify.repositories.TrackRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyTracksClient;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import de.wittenbude.exportify.spotify.data.SpotifyTrackSimplified;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTracksClient spotifyTracksClient;
    private final ArtistService artistService;

    public TrackService(TrackRepository trackRepository,
                        SpotifyTracksClient spotifyTracksClient, ArtistService artistService) {
        this.trackRepository = trackRepository;
        this.spotifyTracksClient = spotifyTracksClient;
        this.artistService = artistService;
    }

    public List<Artist> loadSavedTracks() {
        return List.of();
    }

    public Stream<Track> loadAlbumTracks(String albumID) {
        return SpotifyPage
                .streamPagination(offset -> spotifyTracksClient.getAlbumTracks(albumID, 50, offset))
                .map(SpotifyTrackSimplified::getId)
                .map(spotifyTracksClient::get)
                .map(spotifyTrack -> {
                    List<String> artistIDs = artistService
                            .loadArtists(spotifyTrack.getArtists())
                            .map(Artist::getSpotifyID)
                            .toList();

                    return trackRepository.upsert(TrackConverter.from(spotifyTrack, albumID, artistIDs));
                });
    }
}
