package de.wittenbude.exportify.application.controller;

import de.wittenbude.exportify.application.dto.ArtistSchema;
import de.wittenbude.exportify.application.mapper.ArtistMapper;
import de.wittenbude.exportify.domain.context.artist.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/artists")
class SnapshotArtistsController {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    public SnapshotArtistsController(ArtistService artistService,
                                     ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @GetMapping("/{spotifyArtistID}")
    public ArtistSchema get(@PathVariable("snapshot") UUID snapshot,
                            @PathVariable("spotifyArtistID") String spotifyArtistID) {
        return artistMapper.toDTO(artistService.get(snapshot, spotifyArtistID));
    }

    @GetMapping
    public Collection<ArtistSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return artistService
                .get(snapshot)
                .map(artistMapper::toDTO)
                .toList();
    }

}
