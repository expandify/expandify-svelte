package dev.kenowi.exportify.api.controllers;

import dev.kenowi.exportify.api.dto.SavedAlbumDTO;
import dev.kenowi.exportify.api.mappers.AlbumDtoMapper;
import dev.kenowi.exportify.snapshot.services.AlbumService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.Collection;
import java.util.UUID;

@Path("/snapshot/{snapshot}/albums")
class SnapshotAlbumController {
    private final AlbumService albumService;
    private final AlbumDtoMapper albumDtoMapper;

    public SnapshotAlbumController(AlbumService albumService,
                                   AlbumDtoMapper albumDtoMapper) {
        this.albumService = albumService;
        this.albumDtoMapper = albumDtoMapper;
    }

    @GET
    @Path("/{spotifyAlbumID}")
    public SavedAlbumDTO get(@PathParam("snapshot") UUID snapshot, @PathParam("spotifyAlbumID") String spotifyAlbumID) {
        return albumDtoMapper.toDTO(albumService.get(snapshot, spotifyAlbumID));
    }

    @GET
    public Collection<SavedAlbumDTO> get(@PathParam("snapshot") UUID snapshot) {
        return albumService
                .get(snapshot)
                .map(albumDtoMapper::toDTO)
                .toList();
    }

}
