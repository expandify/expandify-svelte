package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylistSimplified;
import de.wittenbude.expandify.services.spotifydata.SpotifyPlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
    private final SpotifyPlaylistService playlistService;

    public PlaylistController(SpotifyPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/saved")
    public List<SpotifyPlaylistSimplified> loadUserPlaylists(@RequestParam(required = false) Integer offset) throws SpotifyWebApiException {
        return playlistService.loadPlaylists(offset == null ? 0 : offset);
    }

    /*
    @GetMapping("/{id}")
    public SpotifyPlaylist getArtist(@PathVariable String id) {
        return null;
    }
    */
}
