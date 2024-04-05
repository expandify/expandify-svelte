package dev.kenowi.exportify.application.controller;

import dev.kenowi.exportify.application.dto.SnapshotSchema;
import dev.kenowi.exportify.application.mapper.SnapshotMapper;
import dev.kenowi.exportify.domain.service.snapshot.SnapshotService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/snapshot")
class SnapshotController {

    private final SnapshotService snapshotService;
    private final SnapshotMapper snapshotMapper;

    public SnapshotController(SnapshotService snapshotService,
                              SnapshotMapper snapshotMapper) {
        this.snapshotService = snapshotService;
        this.snapshotMapper = snapshotMapper;
    }

    @GetMapping("/{id}")
    public SnapshotSchema get(@PathVariable("id") UUID id) {
        return snapshotMapper.toDTO(snapshotService.get(id));
    }

    @PostMapping
    public SnapshotSchema create() {
        return snapshotMapper.toDTO(snapshotService.create());
    }

}
