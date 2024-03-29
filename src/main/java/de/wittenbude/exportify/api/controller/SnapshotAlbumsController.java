package de.wittenbude.exportify.api.controller;

import de.wittenbude.exportify.album.api.SnapshotAlbumService;
import de.wittenbude.exportify.api.schema.SavedAlbumSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/albums")
class SnapshotAlbumsController {
    private final SnapshotAlbumService albumService;

    public SnapshotAlbumsController(SnapshotAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{spotifyAlbumID}")
    public SavedAlbumSchema get(@PathVariable UUID snapshot, @PathVariable String spotifyAlbumID) {
        return SavedAlbumSchema.from(albumService.get(snapshot, spotifyAlbumID));
    }

    @GetMapping
    public Collection<SavedAlbumSchema> get(@PathVariable UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(SavedAlbumSchema::from)
                .toList();
    }

}
