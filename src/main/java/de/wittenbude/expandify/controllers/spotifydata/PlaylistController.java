package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.SpotifyPlaylist;
import de.wittenbude.expandify.models.spotifydata.PlaylistSimplified;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifydata.PlaylistService;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/latest")
    public List<PlaylistSimplified> getOrLoadLatest() throws SpotifyWebApiException {
        return playlistService.getOrLoadLatest();
    }


    @GetMapping("/{id}")
    public SpotifyPlaylist getPlaylist(@PathVariable String id) {
        return null;
    }

}
