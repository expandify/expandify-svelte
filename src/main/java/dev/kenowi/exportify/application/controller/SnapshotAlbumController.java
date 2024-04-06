package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SavedAlbumSchema;
import dev.kenowi.exportify.application.mapper.AlbumDtoMapper;
import dev.kenowi.exportify.domain.services.album.SnapshotAlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/albums")
class SnapshotAlbumController {
    private final SnapshotAlbumService snapshotAlbumService;
    private final AlbumDtoMapper albumDtoMapper;

    public SnapshotAlbumController(SnapshotAlbumService snapshotAlbumService,
                                   AlbumDtoMapper albumDtoMapper) {
        this.snapshotAlbumService = snapshotAlbumService;
        this.albumDtoMapper = albumDtoMapper;
    }

    @GetMapping("/{spotifyAlbumID}")
    public SavedAlbumSchema get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyAlbumID") String spotifyAlbumID) {
        return albumDtoMapper.toDTO(snapshotAlbumService.get(snapshot, spotifyAlbumID));
    }

    @GetMapping
    public Collection<SavedAlbumSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return snapshotAlbumService
                .get(snapshot)
                .map(albumDtoMapper::toDTO)
                .toList();
    }

}
