package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.services.CacheService;
import de.wittenbude.expandify.services.spotifydata.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    // private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);
    private final AlbumService albumService;
    private final CacheService cacheService;

    public AlbumController(
            AlbumService albumService,
            CacheService cacheService
    ) {
        this.albumService = albumService;
        this.cacheService = cacheService;
    }

    @GetMapping("/latest")
    public List<SavedAlbum> getOrLoadLatest() throws SpotifyWebApiException {
        List<SavedAlbum> cached = cacheService.get().getSavedAlbums();

        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<SavedAlbum> albums = albumService.getLatest();
        return cacheService.setAlbums(albums);
    }


}
