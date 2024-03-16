package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.SpotifyCredentials;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpotifyCredentialsRepository {

    private final Map<String, SpotifyCredentials> repository;

    public SpotifyCredentialsRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public Optional<SpotifyCredentials> getBySpotifyUserID(String id) {
        return Optional.ofNullable(repository.get(id));
    }

    public void upsert(SpotifyCredentials spotifyCredentials) {
        repository.put(spotifyCredentials.getSpotifyUserID(), spotifyCredentials);
    }
}
