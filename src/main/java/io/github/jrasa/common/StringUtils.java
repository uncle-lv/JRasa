package io.github.jrasa.common;

public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return null == s || s.isEmpty();
    }

    private StringUtils() {
        // avoid invoking constructor within class
        throw new AssertionError();
    }
}
