package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SnapshotSchema;
import dev.kenowi.exportify.application.mapper.SnapshotDtoMapper;
import dev.kenowi.exportify.domain.service.snapshot.SnapshotService;
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
    public SnapshotSchema get(@PathVariable("id") UUID id) {
        return snapshotDtoMapper.toDTO(snapshotService.get(id));
    }

    @PostMapping
    public SnapshotSchema create() {
        return snapshotDtoMapper.toDTO(snapshotService.create());
    }

}
