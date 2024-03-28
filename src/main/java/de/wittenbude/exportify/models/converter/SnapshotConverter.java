package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.SnapshotSchema;
import de.wittenbude.exportify.models.Snapshot;

public class SnapshotConverter {

    public static SnapshotSchema toDTO(Snapshot snapshot) {
        return SnapshotSchema
                .builder()
                .id(snapshot.getId())
                .artists(ArtistConverter.toDTOs(snapshot.getArtists()))
                .build();
    }
}
