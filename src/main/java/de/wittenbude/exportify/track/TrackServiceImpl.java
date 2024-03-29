package de.wittenbude.exportify.track;

import de.wittenbude.exportify.artist.api.Artist;
import de.wittenbude.exportify.artist.api.ArtistService;
import de.wittenbude.exportify.spotify.clients.SpotifyTrackClient;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import de.wittenbude.exportify.spotify.data.SpotifyTrackSimplified;
import de.wittenbude.exportify.track.api.SavedTrack;
import de.wittenbude.exportify.track.api.Track;
import de.wittenbude.exportify.track.api.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyTrackClient spotifyClient;
    private final ArtistService artistService;

    public TrackServiceImpl(TrackRepository trackRepository,
                            SpotifyTrackClient spotifyClient,
                            ArtistService artistService) {
        this.trackRepository = trackRepository;
        this.spotifyClient = spotifyClient;
        this.artistService = artistService;
    }

    public Set<SavedTrack> loadSavedTracks() {
        return Set.of();
    }

    public Stream<Track> loadAlbumTracks(String albumID) {
        return SpotifyPage
                .streamPagination(offset -> spotifyClient.getAlbumTracks(albumID, 50, offset))
                .map(SpotifyTrackSimplified::getId)
                .map(spotifyClient::get)
                .map(spotifyTrack -> {
                    List<String> artistIDs = artistService
                            .loadArtists(spotifyTrack.getArtists())
                            .map(Artist::getSpotifyID)
                            .toList();

                    return trackRepository.upsert(spotifyTrack.convert(albumID, artistIDs));
                });
    }
}
