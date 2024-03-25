package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.ArtistEntity;
import de.wittenbude.exportify.db.repositories.ArtistRepository;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.ObjectType;
import de.wittenbude.exportify.spotify.clients.SpotifyArtistsClient;
import de.wittenbude.exportify.spotify.data.SpotifyCursorPage;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
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
        return SpotifyCursorPage.streamPagination(after -> spotifyArtistsClient
                .getFollowing(ObjectType.ARTIST, after, 50)
                .get((ObjectType.ARTIST.getType() + "s")))
                .map(SpotifyArtist::convert)
                .map(ArtistEntity::from)
                .map(artist -> artistRepository
                        .findBySpotifyID(artist.getSpotifyID())
                        .orElseGet(() -> artistRepository.save(artist)))
                .map(ArtistEntity::convert);
    }
}
