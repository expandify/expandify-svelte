package de.wittenbude.exportify.services;

import de.wittenbude.exportify.db.entity.SpotifyUser;
import de.wittenbude.exportify.db.repositories.SpotifyUserRepository;
import de.wittenbude.exportify.spotify.clients.SpotifyUserClient;
import de.wittenbude.exportify.spotify.data.Artist;
import de.wittenbude.exportify.spotify.data.CursorPage;
import de.wittenbude.exportify.spotify.data.PrivateUser;
import de.wittenbude.exportify.spotify.data.SpotifyObjectType;
import org.springframework.stereotype.Service;

@Service
public class SpotifyUserService {


    private final SpotifyUserClient spotifyUserClient;
    private final SpotifyUserRepository spotifyUserRepository;

    public SpotifyUserService(SpotifyUserClient spotifyUserClient, SpotifyUserRepository spotifyUserRepository) {
        this.spotifyUserClient = spotifyUserClient;
        this.spotifyUserRepository = spotifyUserRepository;
    }

    public PrivateUser getCurrentUser(String accessToken) {
        PrivateUser privateUser = spotifyUserClient.getCurrentUser("Bearer " + accessToken);

        spotifyUserRepository.upsert(
                SpotifyUser
                        .builder()
                        .id(privateUser.getId())
                        .country(privateUser.getCountry())
                        .email(privateUser.getEmail())
                        .displayName(privateUser.getDisplayName())
                        .explicitContent(privateUser.getExplicitContent())
                        .uri(privateUser.getUri())
                        .type(privateUser.getType())
                        .followers(privateUser.getFollowers())
                        .href(privateUser.getHref())
                        .product(privateUser.getProduct())
                        .images(privateUser.getImages())
                        .externalUrls(privateUser.getExternalUrls())
                        .build());
        return privateUser;
    }

    public CursorPage<Artist> getCurrentUserFollowedArtists() {
        return spotifyUserClient
                .getFollowing(SpotifyObjectType.ARTIST, null, null)
                .get(SpotifyObjectType.ARTIST.getType() + "s");
    }

}
