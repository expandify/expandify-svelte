package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.SnapshotSchema;
import de.wittenbude.exportify.models.converter.SnapshotConverter;
import de.wittenbude.exportify.services.SnapshotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snapshot")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping("/{id}")
    public SnapshotSchema get(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public SnapshotSchema create() {
        return SnapshotConverter.toDTO(snapshotService.create());
    }

}
