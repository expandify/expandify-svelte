package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.services.spotifydata.PlaylistService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/saved")
    public List<PlaylistSimplified> loadUserPlaylists(@RequestParam(required = false) Integer offset) throws SpotifyWebApiException {
        return playlistService.loadPlaylists(offset == null ? 0 : offset);
    }


    @GetMapping("/{id}")
    public SpotifyPlaylist getPlaylist(@PathVariable String id) {
        return null;
    }

}
