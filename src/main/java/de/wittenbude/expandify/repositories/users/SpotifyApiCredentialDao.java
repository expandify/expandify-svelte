package de.wittenbude.expandify.repositories.users;

import de.wittenbude.expandify.models.db.SpotifyApiCredentials;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpotifyApiCredentialDao implements IDao<SpotifyApiCredentials> {

    private final SpotifyApiCredentialRepository spotifyApiCredentialRepository;

    public SpotifyApiCredentialDao(SpotifyApiCredentialRepository spotifyApiCredentialRepository) {
        this.spotifyApiCredentialRepository = spotifyApiCredentialRepository;
    }

    @Override
    public Optional<SpotifyApiCredentials> find(String id) {
        return spotifyApiCredentialRepository.findById(id);
    }

    @Override
    public Optional<SpotifyApiCredentials> find(SpotifyApiCredentials spotifyApiCredentials) {
        return spotifyApiCredentialRepository.findById(spotifyApiCredentials.getId());
    }

    @Override
    public SpotifyApiCredentials save(SpotifyApiCredentials spotifyApiCredentials) {
        return spotifyApiCredentialRepository.save(spotifyApiCredentials);
    }

    @Override
    public List<SpotifyApiCredentials> saveAll(List<SpotifyApiCredentials> spotifyApiCredentials) {
        return null;
    }
}
