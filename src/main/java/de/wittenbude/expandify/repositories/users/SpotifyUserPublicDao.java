package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyUserPublic;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpotifyUserPublicDao implements IDao<SpotifyUserPublic> {

    private final SpotifyUserPublicRepository spotifyUserPublicRepository;

    public SpotifyUserPublicDao(SpotifyUserPublicRepository spotifyUserPublicRepository) {
        this.spotifyUserPublicRepository = spotifyUserPublicRepository;
    }

    @Override
    public Optional<SpotifyUserPublic> find(String id) {
        return spotifyUserPublicRepository.findById(id);
    }

    @Override
    public Optional<SpotifyUserPublic> find(SpotifyUserPublic spotifyUserPublic) {
        return spotifyUserPublicRepository.findById(spotifyUserPublic.getId());
    }

    @Override
    public SpotifyUserPublic save(SpotifyUserPublic spotifyUserPublic) {
        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        //
        // return this.find(spotifyUser).orElseGet(() -> spotifyUserPublicRepository.save(spotifyUser));
        // TODO still relevant?
        return spotifyUserPublicRepository.save(spotifyUserPublic);
    }

    @Override
    public List<SpotifyUserPublic> saveAll(List<SpotifyUserPublic> spotifyUserPublics) {
        return spotifyUserPublicRepository.saveAll(spotifyUserPublics);
    }
}
