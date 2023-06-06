package io.github.jrasa.common;

public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return null == s || s.isEmpty();
    }

    public static String upperInitial(String s) {
        if (isNullOrEmpty(s)) {
            return s;
        }

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private StringUtils() {
        // avoid invoking constructor within class
        throw new AssertionError();
    }
}
