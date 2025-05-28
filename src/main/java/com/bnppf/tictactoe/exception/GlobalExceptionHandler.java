// src/main/java/com/bnppf/tictactoe/exception/GlobalExceptionHandler.java
package com.bnppf.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map; // For a more structured error response

@ControllerAdvice // This annotation makes it a global handler
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<Object> handleInvalidMoveException(InvalidMoveException ex, WebRequest request) {
        // You can create a more structured error response object if you like
        // For now, a simple map will do, matching the test expectation
        Map<String, String> body = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // You can add more handlers for other custom exceptions or general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        Map<String, String> body = Map.of("message", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}