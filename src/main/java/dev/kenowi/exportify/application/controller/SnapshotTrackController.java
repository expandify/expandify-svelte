package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SavedTrackSchema;
import dev.kenowi.exportify.application.mapper.TrackMapper;
import dev.kenowi.exportify.domain.service.track.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/tracks")
class SnapshotTrackController {
    private final TrackService albumService;
    private final TrackMapper trackMapper;

    public SnapshotTrackController(TrackService albumService,
                                   TrackMapper trackMapper) {
        this.albumService = albumService;
        this.trackMapper = trackMapper;
    }

    @GetMapping("/{spotifyTrackID}")
    public SavedTrackSchema get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyTrackID") String spotifyTrackID) {
        return trackMapper.toDTO(albumService.get(snapshot, spotifyTrackID));
    }

    @GetMapping
    public Collection<SavedTrackSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(trackMapper::toDTO)
                .toList();
    }

}
