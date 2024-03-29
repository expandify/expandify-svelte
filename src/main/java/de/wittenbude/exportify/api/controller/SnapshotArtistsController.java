package de.wittenbude.exportify.api.controller;

import de.wittenbude.exportify.api.schema.ArtistSchema;
import de.wittenbude.exportify.artist.api.SnapshotArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/artists")
class SnapshotArtistsController {
    private final SnapshotArtistService artistService;

    public SnapshotArtistsController(SnapshotArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{spotifyArtistID}")
    public ArtistSchema get(@PathVariable UUID snapshot, @PathVariable String spotifyArtistID) {
        return ArtistSchema.from(artistService.get(snapshot, spotifyArtistID));
    }

    @GetMapping
    public Collection<ArtistSchema> get(@PathVariable UUID snapshot) {
        return artistService
                .get(snapshot)
                .map(ArtistSchema::from)
                .toList();
    }

}
