package io.github.jrasa.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * An ArrayList that can be traversed in reverse.
 *
 * @author uncle-lv
 */
public class ReversibleArrayList<T> extends ArrayList<T> {

    public ReversibleArrayList(Collection<? extends T> c) {
        super(c);
    }

    public ReversibleArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public ReversibleArrayList() {}

    public Iterable<T> reversed() {
        return () -> new Iterator<T>() {
            int cursor = size() - 1;

            @Override
            public boolean hasNext() {
                return cursor > -1;
            }

            @Override
            public T next() {
                return get(cursor--);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
