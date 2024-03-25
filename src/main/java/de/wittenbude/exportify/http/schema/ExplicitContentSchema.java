package de.wittenbude.exportify.http.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.ExplicitContent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExplicitContentSchema {
    @JsonProperty("filter_enabled")
    private final Boolean filterEnabled;

    @JsonProperty("filter_locked")
    private final Boolean filterLocked;

    public static ExplicitContentSchema from(ExplicitContent explicitContent) {
        return ExplicitContentSchema
                .builder()
                .filterEnabled(explicitContent.getFilterEnabled())
                .filterLocked(explicitContent.getFilterLocked())
                .build();
    }
}
