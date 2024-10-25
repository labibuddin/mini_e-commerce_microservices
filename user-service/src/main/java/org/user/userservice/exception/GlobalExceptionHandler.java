package org.user.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user.userservice.model.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotAdminException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // Mengirimkan status 403 Forbidden
    public WebResponse<String> handleUserNotAdminException(UserNotAdminException ex) {
        return WebResponse.<String>builder()
                .data(null)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // Mengirimkan status 403 Forbidden
    public WebResponse<String> handleUserNotExistException(UserNotExistException ex) {
        return WebResponse.<String>builder()
                .data(null)
                .message(ex.getMessage())
                .build();
    }
}

