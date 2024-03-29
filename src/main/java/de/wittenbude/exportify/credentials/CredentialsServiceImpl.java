package de.wittenbude.exportify.credentials;

import de.wittenbude.exportify.credentials.api.SpotifyCredentials;
import de.wittenbude.exportify.credentials.api.SpotifyCredentialsService;
import de.wittenbude.exportify.spotify.data.SpotifyTokenResponse;
import de.wittenbude.exportify.user.api.ExportifyUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
class CredentialsServiceImpl implements SpotifyCredentialsService {

    private final CredentialsRepository credentialsRepository;

    public CredentialsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    public static SpotifyCredentials convert(SpotifyTokenResponse spotifyTokenResponse) {
        return new SpotifyCredentials()
                .setAccessToken(spotifyTokenResponse.getAccessToken())
                .setTokenType(spotifyTokenResponse.getTokenType())
                .setScope(spotifyTokenResponse.getScope())
                .setExpiresAt(Instant.now().plus(spotifyTokenResponse.getExpiresIn(), ChronoUnit.SECONDS))
                .setRefreshToken(spotifyTokenResponse.getRefreshToken());
    }

    @Override
    public SpotifyCredentials link(SpotifyTokenResponse tokenResponse, ExportifyUser user) {
        return credentialsRepository.upsert(convert(tokenResponse)
                .setExportifyUser(user));
    }

    @Override
    public SpotifyCredentials refreshLink(SpotifyTokenResponse newToken, SpotifyCredentials oldCredentials) {
        return credentialsRepository.save(convert(newToken)
                .setExportifyUser(oldCredentials.getExportifyUser())
                .setId(oldCredentials.getId()));
    }
}
