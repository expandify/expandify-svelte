package de.wittenbude.exportify.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Copyright {
    private String text;
    private String type;
}
