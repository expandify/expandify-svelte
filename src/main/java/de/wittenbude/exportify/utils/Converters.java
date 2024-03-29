package de.wittenbude.exportify.utils;

import java.util.function.Function;

public class Converters {

    public static <T, R> R ifNotNull(T t, Function<T, R> f) {
        if (t == null) {
            return null;
        }
        return f.apply(t);
    }
}
