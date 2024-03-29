package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.snapshot.api.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Builder
public class SnapshotSchema {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("artists")
    private Collection<ArtistSchema> artists;

    @JsonProperty("saved_albums")
    private Collection<SavedAlbumSchema> savedAlbums;

    @JsonProperty("saved_tracks")
    private Collection<SavedTrackSchema> savedTracks;

    public static SnapshotSchema from(Snapshot snapshot) {
        return SnapshotSchema
                .builder()
                .id(snapshot.getId())
                .artists(ArtistSchema.from(snapshot.getArtists()))
                .savedAlbums(SavedAlbumSchema.from(snapshot.getSavedAlbums()))
                .savedTracks(SavedTrackSchema.from(snapshot.getSavedTracks()))
                .build();
    }
}
