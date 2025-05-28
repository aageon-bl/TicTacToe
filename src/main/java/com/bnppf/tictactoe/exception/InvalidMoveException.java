// src/main/java/com/bnppf/tictactoe/exception/InvalidMoveException.java
package com.bnppf.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // This makes Spring return 400 Bad Request
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}