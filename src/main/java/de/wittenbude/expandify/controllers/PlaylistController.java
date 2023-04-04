package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.db.Playlist;
import de.wittenbude.expandify.models.db.PlaylistInfo;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import de.wittenbude.expandify.services.spotify.PlaylistService;
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
    private final AuthenticatedUserService authenticatedUserService;

    public PlaylistController(
            PlaylistService playlistService,
            AuthenticatedUserService authenticatedUserService) {
        this.playlistService = playlistService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/saved")
    public List<PlaylistInfo> getSaved() throws SpotifyWebApiException {
        return playlistService.getSaved(authenticatedUserService.getUserId());
    }

    @GetMapping("/{id}")
    public Playlist get(@PathVariable String id) throws SpotifyWebApiException {
        return playlistService.get(id);
    }

}
