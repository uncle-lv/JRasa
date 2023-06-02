package io.github.jrasa.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BeanUtilsTest {

    @Test
    public void testDeepCopy() {
        Person person = new Person(
                "Tom",
                new ArrayList<Friend>(){{
                    add(new Friend("Jack"));
                    add(new Friend("Mary"));
                    add(new Friend("Jim"));
                }}
        );

        Person copy = BeanUtils.deepCopy(person);
        Assertions.assertNotSame(person, copy);
        Assertions.assertNotSame(person.name, copy.name);
        Assertions.assertEquals(person.name, copy.name);
        Assertions.assertNotSame(person.friends, copy.friends);
        Assertions.assertEquals(person.friends.size(), copy.friends.size());
        for (int i = 0; i < person.friends.size(); i++) {
            Assertions.assertEquals(person.friends.get(i), copy.friends.get(i));
            Assertions.assertNotSame(person.friends.get(i), copy.friends.get(i));
            Assertions.assertEquals(person.friends.get(i).name, copy.friends.get(i).name);
            Assertions.assertNotSame(person.friends.get(i).name, copy.friends.get(i).name);
        }

        Assertions.assertNull(BeanUtils.deepCopy(null));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Person {
        private String name;
        private List<Friend> friends;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Friend {
        private String name;
    }
}
