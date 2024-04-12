package dev.kenowi.exportify.snapshot.api.controllers;

import dev.kenowi.exportify.snapshot.api.dto.ArtistDTO;
import dev.kenowi.exportify.snapshot.api.mappers.ArtistDtoMapper;
import dev.kenowi.exportify.snapshot.services.ArtistService;
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
    private final ArtistDtoMapper artistDtoMapper;

    public SnapshotArtistsController(ArtistService artistService,
                                     ArtistDtoMapper artistDtoMapper) {
        this.artistService = artistService;
        this.artistDtoMapper = artistDtoMapper;
    }

    @GetMapping("/{spotifyArtistID}")
    public ArtistDTO get(@PathVariable("snapshot") UUID snapshot,
                         @PathVariable("spotifyArtistID") String spotifyArtistID) {
        return artistDtoMapper.toDTO(artistService.get(snapshot, spotifyArtistID));
    }

    @GetMapping
    public Collection<ArtistDTO> get(@PathVariable("snapshot") UUID snapshot) {
        return artistService
                .get(snapshot)
                .map(artistDtoMapper::toDTO)
                .toList();
    }

}
