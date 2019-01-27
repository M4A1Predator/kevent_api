package com.gamitology.kevent.kevent.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class KeventException extends Exception {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public KeventException(String message) {
        super(message);
    }

    public KeventException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
