package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


public interface ArtistRepository extends CrudRepository<Artist, UUID> {

    Optional<Artist> findBySpotifyID(String id);


    default Artist upsert(Artist artist) {
        this.findBySpotifyID(artist.getSpotifyID())
                .map(Artist::getId)
                .ifPresent(artist::setId);

        return this.save(artist);
    }
}
