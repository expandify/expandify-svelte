package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.Album;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.spotifydata.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);
    private final AlbumService albumService;

    public AlbumController(
            AlbumService albumService
    ) {
        this.albumService = albumService;
    }

    /**
     * Get user saved albums with no track information.
     *
     * @return List of saved tracks.
     * @throws SpotifyWebApiException If any error occurs.
     */
    @GetMapping("/saved")
    public List<SavedAlbum> getSaved() throws SpotifyWebApiException {
        return albumService.getSaved();
    }

    /**
     * Get a single album.
     * This will include all album Tracks.
     *
     * @param id The id of the album.
     * @return The Album
     * @throws SpotifyWebApiException If any error occurs.
     */
    @GetMapping("/{id}")
    public Album get(@PathVariable String id) throws SpotifyWebApiException {
        return albumService.get(id);
    }
}
