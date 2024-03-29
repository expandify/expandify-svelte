package de.wittenbude.exportify.api.controller;

import de.wittenbude.exportify.api.schema.SnapshotSchema;
import de.wittenbude.exportify.snapshot.api.SnapshotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snapshot")
class SnapshotController {

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
        return SnapshotSchema.from(snapshotService.create());
    }

}
