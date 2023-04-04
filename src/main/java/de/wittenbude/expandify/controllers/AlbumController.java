package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.db.Album;
import de.wittenbude.expandify.models.pojos.SavedAlbum;
import de.wittenbude.expandify.services.auth.AuthenticatedUserService;
import de.wittenbude.expandify.services.spotify.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final AuthenticatedUserService authenticatedUserService;

    public AlbumController(
            AlbumService albumService,
            AuthenticatedUserService authenticatedUserService) {
        this.albumService = albumService;
        this.authenticatedUserService = authenticatedUserService;
    }

    /**
     * Get user saved albums with no track information.
     *
     * @return List of saved tracks.
     * @throws SpotifyWebApiException If any error occurs.
     */
    @GetMapping("/saved")
    public List<SavedAlbum> getSaved() throws SpotifyWebApiException {
        return albumService.getSaved(authenticatedUserService.getUserId());
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
