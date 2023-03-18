package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.services.spotifydata.SpotifyArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);
    private final SpotifyArtistService artistService;

    public ArtistController(SpotifyArtistService artistService) {
        this.artistService = artistService;
    }

}
