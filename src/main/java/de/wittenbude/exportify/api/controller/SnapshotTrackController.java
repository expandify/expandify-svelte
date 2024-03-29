package de.wittenbude.exportify.api.controller;

import de.wittenbude.exportify.api.schema.SavedTrackSchema;
import de.wittenbude.exportify.track.api.SnapshotTrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/tracks")
class SnapshotTrackController {
    private final SnapshotTrackService albumService;

    public SnapshotTrackController(SnapshotTrackService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{spotifyTrackID}")
    public SavedTrackSchema get(@PathVariable UUID snapshot, @PathVariable String spotifyTrackID) {
        return SavedTrackSchema.from(albumService.get(snapshot, spotifyTrackID));
    }

    @GetMapping
    public Collection<SavedTrackSchema> get(@PathVariable UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(SavedTrackSchema::from)
                .toList();
    }

}
