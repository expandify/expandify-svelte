package de.wittenbude.exportify.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CollectionEquality {

    public static <K, V> boolean equals(Map<K, V> a, Map<K, V> b) {

        if (a == null) {
            return b == null;
        }
        if (b == null) {
            return false;
        }

        return new HashMap<>(a).equals(new HashMap<>(b));
    }

    public static <E> boolean equals(Collection<E> a, Collection<E> b) {

        if (a == null) {
            return b == null;
        }
        if (b == null) {
            return false;
        }

        return new HashSet<>(a).equals(new HashSet<>(b));
    }
}
