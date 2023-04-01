package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifydata.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/latest")
    public List<SavedAlbum> getOrLoadLatest() throws SpotifyWebApiException {
        return albumService.getOrLoadLatest();
    }


    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable String id) {
        return null;
    }


}
