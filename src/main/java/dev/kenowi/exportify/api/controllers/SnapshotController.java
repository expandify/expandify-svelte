package dev.kenowi.exportify.api.controllers;

import dev.kenowi.exportify.api.dto.SnapshotDTO;
import dev.kenowi.exportify.api.mappers.SnapshotDtoMapper;
import dev.kenowi.exportify.snapshot.services.SnapshotService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@Path("/snapshot")
class SnapshotController {

    private final SnapshotService snapshotService;
    private final SnapshotDtoMapper snapshotDtoMapper;

    public SnapshotController(SnapshotService snapshotService,
                              SnapshotDtoMapper snapshotDtoMapper) {
        this.snapshotService = snapshotService;
        this.snapshotDtoMapper = snapshotDtoMapper;
    }

    @GET
    @Path("/{id}")
    @Transactional
    public SnapshotDTO get(@PathParam("id") UUID id) {
        return snapshotDtoMapper.toDTO(snapshotService.get(id));
    }

    @POST
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    public SnapshotDTO create() {
        return snapshotDtoMapper.toDTO(snapshotService.create());
    }

}
