package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyUserPrivate;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpotifyUserPrivateDao implements IDao<SpotifyUserPrivate> {

    private final SpotifyUserPrivateRepository spotifyUserPrivateRepository;

    public SpotifyUserPrivateDao(SpotifyUserPrivateRepository spotifyUserPrivateRepository) {
        this.spotifyUserPrivateRepository = spotifyUserPrivateRepository;
    }

    @Override
    public Optional<SpotifyUserPrivate> find(String id) {
        return spotifyUserPrivateRepository.findById(id);
    }

    @Override
    public Optional<SpotifyUserPrivate> find(SpotifyUserPrivate spotifyUserPrivate) {
        return spotifyUserPrivateRepository.findById(spotifyUserPrivate.getId());
    }

    @Override
    public SpotifyUserPrivate save(SpotifyUserPrivate spotifyUserPrivate) {
        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        //
        // return this.find(spotifyUser).orElseGet(() -> spotifyUserPrivateRepository.save(spotifyUser));
        // TODO still relevant?
        return spotifyUserPrivateRepository.save(spotifyUserPrivate);
    }

    @Override
    public List<SpotifyUserPrivate> saveAll(List<SpotifyUserPrivate> spotifyUserPrivates) {
        return spotifyUserPrivateRepository.saveAll(spotifyUserPrivates);
    }
}
