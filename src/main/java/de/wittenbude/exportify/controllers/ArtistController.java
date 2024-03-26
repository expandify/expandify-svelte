package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.ArtistSchema;
import de.wittenbude.exportify.services.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/followed")
    public List<ArtistSchema> followed() {
        return artistService
                .loadCurrentUserFollowedArtists()
                .map(ArtistSchema::from)
                .toList();
    }
}
