package de.wittenbude.exportify.application.controller;

import de.wittenbude.exportify.application.dto.SnapshotSchema;
import de.wittenbude.exportify.application.mapper.SnapshotMapper;
import de.wittenbude.exportify.domain.context.snapshot.SnapshotService;
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
