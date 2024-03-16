package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpotifyUserRepository {

    private final Map<String, SpotifyUser> repository;

    public SpotifyUserRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public Optional<SpotifyUser> getByID(String id) {
        return Optional.ofNullable(repository.get(id));
    }

    public void upsert(SpotifyUser spotifyUser) {
        repository.put(spotifyUser.getId(), spotifyUser);
    }

}
