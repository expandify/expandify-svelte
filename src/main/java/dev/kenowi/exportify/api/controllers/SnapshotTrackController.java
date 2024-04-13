package dev.kenowi.exportify.api.controllers;

import dev.kenowi.exportify.api.dto.SavedTrackDTO;
import dev.kenowi.exportify.api.mappers.TrackDtoMapper;
import dev.kenowi.exportify.snapshot.services.TrackService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.Collection;
import java.util.UUID;

@Path("/snapshot/{snapshot}/tracks")
class SnapshotTrackController {
    private final TrackService trackService;
    private final TrackDtoMapper trackDtoMapper;

    public SnapshotTrackController(TrackService trackService,
                                   TrackDtoMapper trackDtoMapper) {
        this.trackService = trackService;
        this.trackDtoMapper = trackDtoMapper;
    }

    @GET
    @Path("/{spotifyTrackID}")
    public SavedTrackDTO get(@PathParam("snapshot") UUID snapshot, @PathParam("spotifyTrackID") String spotifyTrackID) {
        return trackDtoMapper.toDTO(trackService.get(snapshot, spotifyTrackID));
    }

    @GET
    public Collection<SavedTrackDTO> get(@PathParam("snapshot") UUID snapshot) {
        return trackService
                .get(snapshot)
                .map(trackDtoMapper::toDTO)
                .toList();
    }

}
