package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyArtist;
import de.wittenbude.expandify.repositories.ArtistRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SpotifyArtistService {

    // private static final Logger LOG = LoggerFactory.getLogger(SpotifyArtistService.class);
    private final ArtistRepository artistRepository;
    private final SpotifyApiRequestService spotifyApiRequest;
    private final SpotifyLibraryService libraryService;

    public SpotifyArtistService(
            ArtistRepository artistRepository,
            SpotifyApiRequestService spotifyApiRequest,
            SpotifyLibraryService libraryService
    ) {
        this.artistRepository = artistRepository;
        this.spotifyApiRequest = spotifyApiRequest;
        this.libraryService = libraryService;
    }

    public List<SpotifyArtist> loadFollowedArtists(String after) throws SpotifyWebApiException {
        Stream<Artist> stream;

        if (after != null) {
            stream = Arrays.stream(spotifyApiRequest
                    .makeRequest(api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST).after(after))
                    .getItems());
        } else {
            stream = Arrays.stream(spotifyApiRequest
                    .makeRequest(api -> api.getUsersFollowedArtists(ModelObjectType.ARTIST))
                    .getItems());
        }

        List<SpotifyArtist> artists = stream
                .map(this::saveWithTracks)
                .toList();

        return libraryService.saveArtistsToLatest(artists);
    }

    private SpotifyArtist saveWithTracks(Artist artist) {
        SpotifyArtist spotifyArtist = artistRepository
                .findById(artist.getId())
                .orElse(new SpotifyArtist(artist));

        return artistRepository.save(spotifyArtist);
    }

}
