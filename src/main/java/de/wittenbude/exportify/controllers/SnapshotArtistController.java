package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.ArtistSchema;
import de.wittenbude.exportify.models.converter.ArtistConverter;
import de.wittenbude.exportify.services.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/artists")
public class SnapshotArtistController {
    private final ArtistService artistService;

    public SnapshotArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{spotifyArtistID}")
    public ArtistSchema get(@PathVariable UUID snapshot, @PathVariable String spotifyArtistID) {
        return ArtistConverter.toDTO(artistService.get(snapshot, spotifyArtistID));
    }

    @GetMapping
    public Collection<ArtistSchema> get(@PathVariable UUID snapshot) {
        return artistService
                .get(snapshot)
                .map(ArtistConverter::toDTO)
                .toList();
    }

}
