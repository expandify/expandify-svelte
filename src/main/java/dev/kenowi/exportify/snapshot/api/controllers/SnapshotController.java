package dev.kenowi.exportify.snapshot.api.controllers;

import dev.kenowi.exportify.snapshot.api.dto.SnapshotDTO;
import dev.kenowi.exportify.snapshot.api.mappers.SnapshotDtoMapper;
import dev.kenowi.exportify.snapshot.services.SnapshotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/snapshot")
class SnapshotController {

    private final SnapshotService snapshotService;
    private final SnapshotDtoMapper snapshotDtoMapper;

    public SnapshotController(SnapshotService snapshotService,
                              SnapshotDtoMapper snapshotDtoMapper) {
        this.snapshotService = snapshotService;
        this.snapshotDtoMapper = snapshotDtoMapper;
    }

    @GetMapping("/{id}")
    public SnapshotDTO get(@PathVariable("id") UUID id) {
        return snapshotDtoMapper.toDTO(snapshotService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SnapshotDTO create() {
        return snapshotDtoMapper.toDTO(snapshotService.create());
    }

}
