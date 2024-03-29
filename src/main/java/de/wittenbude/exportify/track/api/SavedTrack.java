package de.wittenbude.exportify.track.api;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn
    private Track track;
}
