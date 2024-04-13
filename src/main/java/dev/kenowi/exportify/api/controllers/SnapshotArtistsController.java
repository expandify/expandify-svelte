package dev.kenowi.exportify.api.controllers;

import dev.kenowi.exportify.api.dto.ArtistDTO;
import dev.kenowi.exportify.api.mappers.ArtistDtoMapper;
import dev.kenowi.exportify.snapshot.services.ArtistService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.Collection;
import java.util.UUID;

@Path("/snapshot/{snapshot}/artists")
class SnapshotArtistsController {
    private final ArtistService artistService;
    private final ArtistDtoMapper artistDtoMapper;

    public SnapshotArtistsController(ArtistService artistService,
                                     ArtistDtoMapper artistDtoMapper) {
        this.artistService = artistService;
        this.artistDtoMapper = artistDtoMapper;
    }

    @GET
    @Path("/{spotifyArtistID}")
    public ArtistDTO get(@PathParam("snapshot") UUID snapshot,
                         @PathParam("spotifyArtistID") String spotifyArtistID) {
        return artistDtoMapper.toDTO(artistService.get(snapshot, spotifyArtistID));
    }

    @GET
    public Collection<ArtistDTO> get(@PathParam("snapshot") UUID snapshot) {
        return artistService
                .get(snapshot)
                .map(artistDtoMapper::toDTO)
                .toList();
    }

}
