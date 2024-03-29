package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.SavedAlbumSchema;
import de.wittenbude.exportify.models.converter.AlbumConverter;
import de.wittenbude.exportify.services.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/albums")
public class SnapshotAlbumsController {
    private final AlbumService albumService;

    public SnapshotAlbumsController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{spotifyAlbumID}")
    public SavedAlbumSchema get(@PathVariable UUID snapshot, @PathVariable String spotifyAlbumID) {
        return AlbumConverter.toDTO(albumService.get(snapshot, spotifyAlbumID));
    }

    @GetMapping
    public Collection<SavedAlbumSchema> get(@PathVariable UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(AlbumConverter::toDTO)
                .toList();
    }

}
