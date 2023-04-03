package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Playlist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.services.spotifydata.PlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    // private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/saved")
    public List<PlaylistSimplified> getSaved() throws SpotifyWebApiException {
        return playlistService.getSaved();
    }

    @GetMapping("/{id}")
    public Playlist get(@PathVariable String id) throws SpotifyWebApiException {
        return playlistService.get(id);
    }

}
