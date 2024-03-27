package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.models.embeds.Copyright;
import de.wittenbude.exportify.spotify.data.SpotifyCopyright;

import java.util.Arrays;
import java.util.List;

public class CopyrightConverter {

    public static Copyright from(SpotifyCopyright spotifyCopyright) {
        return new Copyright()
                .setText(spotifyCopyright.getText())
                .setType(spotifyCopyright.getType().getType());
    }

    public static List<Copyright> from(SpotifyCopyright[] copyrights) {
        return Arrays
                .stream(copyrights)
                .map(CopyrightConverter::from)
                .toList();
    }
}
