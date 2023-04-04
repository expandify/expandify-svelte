package de.wittenbude.expandify.repositories.artists;

import de.wittenbude.expandify.models.db.Artist;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArtistDao implements IDao<Artist> {

    private final ArtistRepository artistRepository;

    public ArtistDao(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Optional<Artist> find(String id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<Artist> find(Artist artist) {
        return artistRepository.findById(artist.getId());
    }

    @Override
    public Artist save(Artist album) {
        return artistRepository.save(album);
    }

    @Override
    public List<Artist> saveAll(List<Artist> artists) {
        return artistRepository.saveAll(artists);
    }
}
