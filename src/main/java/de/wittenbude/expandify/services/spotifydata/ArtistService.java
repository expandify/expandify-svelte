package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Artist;
import de.wittenbude.expandify.repositories.ArtistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Service
public class ArtistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final ArtistRepository artistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public ArtistService(
            ArtistRepository artistRepository,
            SpotifyApiRequestService spotifyApiRequest
    ) {
        this.artistRepository = artistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }

    public List<Artist> loadFollowedArtists(String after) throws SpotifyWebApiException {
        Function<SpotifyApi, GetUsersFollowedArtistsRequest.Builder> f = api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST);

        if (after != null) {
            f = f.andThen(builder -> builder.after(after));
        }

        return Arrays.stream(spotifyApiRequest.makeRequest(f::apply)
                        .getItems())
                .map(this::save)
                .toList();
    }

    private Artist save(se.michaelthelin.spotify.model_objects.specification.Artist artist) {
        return artistRepository
                .findById(artist.getId())
                .orElse(artistRepository.save(new Artist(artist)));
    }

}
