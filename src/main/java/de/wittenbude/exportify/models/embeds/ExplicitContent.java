package de.wittenbude.exportify.models.embeds;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ExplicitContent {
    private Boolean filterEnabled;
    private Boolean filterLocked;
}
