package io.github.jrasa.exception;

import io.github.jrasa.common.StringUtils;
import lombok.Getter;

@Getter
public class ActionNotFoundException extends Exception {
    private final String actionName;

    public ActionNotFoundException(String actionName) {
        this(actionName, null);
    }

    public ActionNotFoundException(String actionName, String message) {
        super(StringUtils.isNullOrEmpty(message) ? String.format("No registered action found for name '%s'.", actionName) : message);
        this.actionName = actionName;
    }
}
