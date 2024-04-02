package de.wittenbude.exportify.domain.context.artist;

import de.wittenbude.exportify.domain.entities.Artist;
import de.wittenbude.exportify.domain.events.AlbumLoadedEvent;
import de.wittenbude.exportify.domain.events.ArtistsLoadedEvent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface ArtistService {

    Set<Artist> loadFollowedArtists();

    Artist get(UUID snapshot, String spotifyArtistID);

    Stream<Artist> get(UUID snapshot);

    // TODO this needs to be public because of the event (not cool)
    void loadArtists(AlbumLoadedEvent event);

    // TODO this needs to be public because of the event (not cool)
    void persistArtists(ArtistsLoadedEvent event);

}
