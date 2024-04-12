package dev.kenowi.exportify.snapshot.api.controllers;

import dev.kenowi.exportify.snapshot.api.dto.SavedTrackDTO;
import dev.kenowi.exportify.snapshot.api.mappers.TrackDtoMapper;
import dev.kenowi.exportify.snapshot.services.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/snapshot/{snapshot}/tracks")
class SnapshotTrackController {
    private final TrackService trackService;
    private final TrackDtoMapper trackDtoMapper;

    public SnapshotTrackController(TrackService trackService,
                                   TrackDtoMapper trackDtoMapper) {
        this.trackService = trackService;
        this.trackDtoMapper = trackDtoMapper;
    }

    @GetMapping("/{spotifyTrackID}")
    public SavedTrackDTO get(@PathVariable("snapshot") UUID snapshot, @PathVariable("spotifyTrackID") String spotifyTrackID) {
        return trackDtoMapper.toDTO(trackService.get(snapshot, spotifyTrackID));
    }

    @GetMapping
    public Collection<SavedTrackDTO> get(@PathVariable("snapshot") UUID snapshot) {
        return trackService
                .get(snapshot)
                .map(trackDtoMapper::toDTO)
                .toList();
    }

}
