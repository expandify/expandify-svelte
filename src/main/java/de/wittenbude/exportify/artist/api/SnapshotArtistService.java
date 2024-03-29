package de.wittenbude.exportify.artist.api;

import java.util.UUID;
import java.util.stream.Stream;

public interface SnapshotArtistService {
    Artist get(UUID snapshot, String spotifyArtistID);

    Stream<Artist> get(UUID snapshot);
}
