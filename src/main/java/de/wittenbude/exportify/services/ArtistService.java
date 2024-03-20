package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.ArtistEntity;
import de.wittenbude.exportify.db.repositories.ArtistRepository;
import de.wittenbude.exportify.models.Artist;
import de.wittenbude.exportify.models.SpotifyObjectType;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import de.wittenbude.exportify.spotify.data.CursorPage;
import de.wittenbude.exportify.spotify.data.SpotifyArtist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class ArtistService {

    private final SpotifyUserClient spotifyUserClient;
    private final ArtistRepository artistRepository;

    public ArtistService(SpotifyUserClient spotifyUserClient,
                         ArtistRepository artistRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.artistRepository = artistRepository;
    }

    public static <T> Stream<T> stream(Function<String, CursorPage<T>> pageSupplier) {
        CursorPage<T> firstPage = pageSupplier.apply(null);
        if (firstPage == null || !firstPage.hasContent()) {
            return Stream.empty();
        }

        return Stream.iterate(firstPage, p -> !p.isEmpty(), p -> p.hasNext() ? pageSupplier.apply(p.getCursors().getAfter()) : CursorPage.empty())
                .flatMap(p -> p.getItems().stream());
    }

    public List<Artist> loadCurrentUserFollowedArtists() {
        // TODO connect artists with current user
        return stream(after -> spotifyUserClient
                .getFollowing(SpotifyObjectType.ARTIST, after, 50)
                .get((SpotifyObjectType.ARTIST.getType() + "s")))
                .map(SpotifyArtist::convert)
                .map(ArtistEntity::from)
                .map(artist -> artistRepository
                        .findBySpotifyID(artist.getSpotifyID())
                        .orElseGet(() -> artistRepository.save(artist)))
                .map(ArtistEntity::convert)
                .toList();
    }
}
