package de.wittenbude.exportify.services;

import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.repositories.ArtistRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyArtistsClient;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ArtistService {

    private final SpotifyArtistsClient spotifyArtistsClient;
    private final ArtistRepository artistRepository;

    public ArtistService(SpotifyArtistsClient spotifyArtistsClient,
                         ArtistRepository artistRepository) {
        this.spotifyArtistsClient = spotifyArtistsClient;
        this.artistRepository = artistRepository;
    }


    public Stream<Artist> loadCurrentUserFollowedArtists() {
        // TODO connect artists with current user
        return SpotifyCursorPage
                .streamPagination(after -> spotifyArtistsClient
                        .getFollowing(after, 50)
                        .get("artists"))
                .map(SpotifyArtist::convert)
                .map(artist -> artistRepository
                        .findBySpotifyID(artist.getSpotifyID())
                        .orElseGet(() -> artistRepository.save(artist)));
    }
}
