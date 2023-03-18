package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.services.spotifydata.SpotifyPlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
    private final SpotifyPlaylistService playlistService;

    public PlaylistController(SpotifyPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

}
