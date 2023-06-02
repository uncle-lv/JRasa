package io.github.jrasa.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ReversibleArrayListTest {

    private ReversibleArrayList<String> reversibleArrayList;
    private List<String> list;

    @BeforeEach
    public void init() {
        String[] strings = new String[]{"first", "second", "third"};
        this.list = Arrays.asList(strings);
        reversibleArrayList = new ReversibleArrayList<>(this.list);
    }

    @Test
    public void testReversed() {
        int idx = this.list.size() - 1;
        for (String s : this.reversibleArrayList.reversed()) {
            Assertions.assertEquals(list.get(idx), s);
            idx -= 1;
        }
    }

    @Test
    public void testRemove() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> this.reversibleArrayList.reversed().iterator().remove());
    }
}
