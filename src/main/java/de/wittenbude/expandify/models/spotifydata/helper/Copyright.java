package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.enums.CopyrightType;

@Data
@NoArgsConstructor
public class Copyright {
    private String text;
    private CopyrightType type;

    public Copyright(se.michaelthelin.spotify.model_objects.specification.Copyright copyright) {
        this.text = copyright.getText();
        this.type = copyright.getType();
    }
}
