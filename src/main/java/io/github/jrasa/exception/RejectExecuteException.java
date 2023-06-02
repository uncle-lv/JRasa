package io.github.jrasa.exception;

import io.github.jrasa.common.StringUtils;
import lombok.Getter;

/**
 * Raising this exception will allow other policies to predict another action.
 *
 * @author uncle-lv
 */
@Getter
public class RejectExecuteException extends Exception {
    private final String actionName;

    public RejectExecuteException(String actionName) {
        this(actionName, null);
    }

    public RejectExecuteException(String actionName, String message) {
        super(StringUtils.isNullOrEmpty(message) ? String.format("Custom action '%s' rejected execution.", actionName) : message);
        this.actionName = actionName;
    }
}
