package server;

import io.github.jrasa.exception.ActionNotFoundException;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.rest.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class JRasaExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RejectExecuteException.class)
    protected ResponseEntity<ErrorResponse> handleRejectExecuteException(RejectExecuteException e) {
        log.debug(e.getMessage());
        ErrorResponse error = new ErrorResponse(e.getMessage(), e.getActionName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActionNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleActionNotFoundException(ActionNotFoundException e) {
        log.debug(e.getMessage());
        ErrorResponse error = new ErrorResponse(e.getMessage(), e.getActionName());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
