package de.wittenbude.expandify.controllers.spotifydata;

import de.wittenbude.expandify.services.spotifydata.SpotifyLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryController.class);
    private final SpotifyLibraryService libraryService;

    public LibraryController(SpotifyLibraryService libraryService) {
        this.libraryService = libraryService;
    }




}
