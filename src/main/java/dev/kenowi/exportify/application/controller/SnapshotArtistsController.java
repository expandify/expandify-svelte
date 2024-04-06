package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.ArtistSchema;
import dev.kenowi.exportify.application.mapper.ArtistDtoMapper;
import dev.kenowi.exportify.domain.service.artist.SnapshotArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/artists")
class SnapshotArtistsController {
    private final SnapshotArtistService snapshotArtistService;
    private final ArtistDtoMapper artistDtoMapper;

    public SnapshotArtistsController(SnapshotArtistService snapshotArtistService,
                                     ArtistDtoMapper artistDtoMapper) {
        this.snapshotArtistService = snapshotArtistService;
        this.artistDtoMapper = artistDtoMapper;
    }

    @GetMapping("/{spotifyArtistID}")
    public ArtistSchema get(@PathVariable("snapshot") UUID snapshot,
                            @PathVariable("spotifyArtistID") String spotifyArtistID) {
        return artistDtoMapper.toDTO(snapshotArtistService.get(snapshot, spotifyArtistID));
    }

    @GetMapping
    public Collection<ArtistSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return snapshotArtistService
                .get(snapshot)
                .map(artistDtoMapper::toDTO)
                .toList();
    }

}
