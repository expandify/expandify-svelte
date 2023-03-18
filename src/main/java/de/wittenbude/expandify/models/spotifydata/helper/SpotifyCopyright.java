package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.enums.CopyrightType;
import se.michaelthelin.spotify.model_objects.specification.Copyright;

@Data
@NoArgsConstructor
public class SpotifyCopyright {
    private String text;
    private CopyrightType type;

    public SpotifyCopyright(Copyright copyright) {
        this.text = copyright.getText();
        this.type = copyright.getType();
    }
}
