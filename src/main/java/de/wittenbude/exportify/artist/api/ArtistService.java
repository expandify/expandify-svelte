package de.wittenbude.exportify.artist.api;

import de.wittenbude.exportify.spotify.data.SpotifyArtistSimplified;

import java.util.List;
import java.util.stream.Stream;

public interface ArtistService {

    Stream<Artist> loadFollowedArtists();

    Stream<Artist> loadArtists(SpotifyArtistSimplified[] artists);

    Stream<Artist> loadArtists(List<String> spotifyIDs);
}
