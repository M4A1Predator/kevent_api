package com.gamitology.kevent.kevent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({KeventException.class})
    public ResponseEntity<ApiError> handleAppEx(KeventException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(apiError);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> catchAll(Exception ex) {
        ex.printStackTrace();
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
