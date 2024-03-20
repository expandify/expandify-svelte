package de.wittenbude.exportify.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExplicitContent {
    private Boolean filterEnabled;
    private Boolean filterLocked;
}
