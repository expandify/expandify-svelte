package dev.kenowi.exportify.domain.entities.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Embeddable
@EqualsAndHashCode
public class ExternalIDs {
    private String isrc;
    private String ean;
    private String upc;
}
