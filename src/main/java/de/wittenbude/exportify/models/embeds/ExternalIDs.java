package de.wittenbude.exportify.models.embeds;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Embeddable
public class ExternalIDs {

    private String isrc;
    private String ean;
    private String upc;
}
