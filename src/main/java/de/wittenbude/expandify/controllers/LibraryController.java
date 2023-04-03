package de.wittenbude.expandify.controllers;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.services.spotifydata.LibraryService;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    // private static final Logger LOG = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    @PostMapping()
    public Library save() throws SpotifyWebApiException {
        return libraryService.createLibrary();
    }

    @GetMapping("/{id}")
    public Library getById(@PathVariable String id) {
        return libraryService.getById(id);
    }

    @GetMapping("/ids")
    public List<String> getAllIds() {
        return libraryService
                .getAll()
                .stream()
                .map(Library::getId)
                .toList();
    }



}
