package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SavedAlbumSchema;
import dev.kenowi.exportify.application.mapper.AlbumMapper;
import dev.kenowi.exportify.domain.service.album.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/albums")
class SnapshotAlbumsController {
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    public SnapshotAlbumsController(AlbumService albumService,
                                    AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping("/{spotifyAlbumID}")
    public SavedAlbumSchema get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyAlbumID") String spotifyAlbumID) {
        return albumMapper.toDTO(albumService.get(snapshot, spotifyAlbumID));
    }

    @GetMapping
    public Collection<SavedAlbumSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(albumMapper::toDTO)
                .toList();
    }

}
