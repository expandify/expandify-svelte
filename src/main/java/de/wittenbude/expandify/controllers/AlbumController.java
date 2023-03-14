package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.spotifydata.SpotifyAlbum;
import de.wittenbude.expandify.services.spotifydata.SpotifyAlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);

    private final SpotifyAlbumService spotifyAlbumService;

    public AlbumController(SpotifyAlbumService spotifyAlbumService) {
        this.spotifyAlbumService = spotifyAlbumService;
    }

    @GetMapping()
    public String getAll() {
        LOG.debug("Getting albums");

        return "YEAH!!!";
    }

    @GetMapping("/abc")
    public List<SpotifyAlbum> getASD() throws SpotifyWebApiException {
        return spotifyAlbumService.getUserAlbums();
    }
}
