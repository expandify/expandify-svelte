package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SavedTrackSchema;
import dev.kenowi.exportify.application.mapper.TrackDtoMapper;
import dev.kenowi.exportify.domain.service.track.SnapshotTrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/tracks")
class SnapshotTrackController {
    private final SnapshotTrackService snapshotTrackService;
    private final TrackDtoMapper trackDtoMapper;

    public SnapshotTrackController(SnapshotTrackService snapshotTrackService,
                                   TrackDtoMapper trackDtoMapper) {
        this.snapshotTrackService = snapshotTrackService;
        this.trackDtoMapper = trackDtoMapper;
    }

    @GetMapping("/{spotifyTrackID}")
    public SavedTrackSchema get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyTrackID") String spotifyTrackID) {
        return trackDtoMapper.toDTO(snapshotTrackService.get(snapshot, spotifyTrackID));
    }

    @GetMapping
    public Collection<SavedTrackSchema> get(@PathVariable("snapshot") UUID snapshot) {
        return snapshotTrackService
                .get(snapshot)
                .map(trackDtoMapper::toDTO)
                .toList();
    }

}
