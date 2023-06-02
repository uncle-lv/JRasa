package io.github.jrasa.common;

public class ActionNameChecker {
    public static boolean isProbablyActionName(String actionName) {
        if (StringUtils.isNullOrEmpty(actionName)) {
            return false;
        }

        return actionName.startsWith("utter_") || actionName.startsWith("action_");
    }

    private ActionNameChecker() {
        // avoid invoking constructor within class
        throw new AssertionError();
    }
}
