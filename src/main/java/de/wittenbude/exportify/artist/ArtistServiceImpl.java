package de.wittenbude.exportify.artist;

import de.wittenbude.exportify.artist.api.Artist;
import de.wittenbude.exportify.artist.api.ArtistService;
import de.wittenbude.exportify.spotify.clients.SpotifyArtistClient;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import de.wittenbude.exportify.spotify.data.SpotifyArtistSimplified;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
class ArtistServiceImpl implements ArtistService {

    private final SpotifyArtistClient spotifyArtistClient;
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(SpotifyArtistClient spotifyArtistClient,
                             ArtistRepository artistRepository) {
        this.spotifyArtistClient = spotifyArtistClient;
        this.artistRepository = artistRepository;
    }


    public Stream<Artist> loadFollowedArtists() {
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(SpotifyArtist::convert)
                .map(artistRepository::upsert);
    }

    public Stream<Artist> loadArtists(SpotifyArtistSimplified[] artists) {
        return loadArtists(Arrays.stream(artists).map(SpotifyArtistSimplified::getId).toList());
    }

    public Stream<Artist> loadArtists(List<String> spotifyIDs) {
        return spotifyArtistClient
                .getArtists(String.join(",", spotifyIDs))
                .get("artists")
                .stream()
                .map(SpotifyArtist::convert)
                .map(artistRepository::upsert);
    }
}
