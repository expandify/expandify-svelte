package de.wittenbude.exportify.domain.valueobjects;

import de.wittenbude.exportify.domain.entities.Track;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@Embeddable
public class SavedTrack {

    private Instant savedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Track track;
}
