package dev.kenowi.exportify.snapshot.api.controllers;

import dev.kenowi.exportify.snapshot.api.dto.SavedAlbumDTO;
import dev.kenowi.exportify.snapshot.api.mappers.AlbumDtoMapper;
import dev.kenowi.exportify.snapshot.services.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/albums")
class SnapshotAlbumController {
    private final AlbumService albumService;
    private final AlbumDtoMapper albumDtoMapper;

    public SnapshotAlbumController(AlbumService albumService,
                                   AlbumDtoMapper albumDtoMapper) {
        this.albumService = albumService;
        this.albumDtoMapper = albumDtoMapper;
    }

    @GetMapping("/{spotifyAlbumID}")
    public SavedAlbumDTO get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyAlbumID") String spotifyAlbumID) {
        return albumDtoMapper.toDTO(albumService.get(snapshot, spotifyAlbumID));
    }

    @GetMapping
    public Collection<SavedAlbumDTO> get(@PathVariable("snapshot") UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(albumDtoMapper::toDTO)
                .toList();
    }

}
