package io.github.jrasa.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionNameCheckerTest {

    @Test
    public void testIsProbablyName() {
        Assertions.assertFalse(ActionNameChecker.isProbablyActionName(null));
        Assertions.assertFalse(ActionNameChecker.isProbablyActionName(""));
        Assertions.assertFalse(ActionNameChecker.isProbablyActionName("answer"));
        Assertions.assertTrue(ActionNameChecker.isProbablyActionName("utter_greet"));
        Assertions.assertTrue(ActionNameChecker.isProbablyActionName("action_greet"));
    }
}
