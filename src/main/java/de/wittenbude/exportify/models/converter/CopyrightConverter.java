package de.wittenbude.exportify.models.converter;

import de.wittenbude.exportify.dto.CopyrightSchema;
import de.wittenbude.exportify.models.embeds.Copyright;
import de.wittenbude.exportify.spotify.data.SpotifyCopyright;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CopyrightConverter {

    public static Copyright from(SpotifyCopyright spotifyCopyright) {
        return new Copyright()
                .setText(spotifyCopyright.getText())
                .setType(spotifyCopyright.getType());
    }

    public static List<Copyright> from(SpotifyCopyright[] copyrights) {
        return Arrays
                .stream(copyrights)
                .map(CopyrightConverter::from)
                .toList();
    }

    public static CopyrightSchema toDTO(Copyright copyright) {
        return CopyrightSchema
                .builder()
                .text(copyright.getText())
                .type(copyright.getType())
                .build();
    }

    public static Collection<CopyrightSchema> toDTOs(Collection<Copyright> copyrights) {
        return copyrights
                .stream()
                .map(CopyrightConverter::toDTO)
                .toList();
    }
}
