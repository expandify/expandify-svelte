package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResumePoint {
    private Boolean fullyPlayed;
    private Integer resumePositionMs;

    public ResumePoint(final se.michaelthelin.spotify.model_objects.specification.ResumePoint resumePoint) {
        this.fullyPlayed = resumePoint.getFullyPlayed();
        this.resumePositionMs = resumePoint.getResumePositionMs();
    }
}
