package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.services.spotifydata.SpotifyAlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);
    private final SpotifyAlbumService albumService;

    public AlbumController(SpotifyAlbumService albumService) {
        this.albumService = albumService;
    }




}
