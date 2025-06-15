package com.example.unoback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException; // Import for missing path variable
import org.springframework.web.bind.MissingServletRequestParameterException; // Import for missing request param
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException; // Import for 404 (no handler found)
import org.springframework.http.converter.HttpMessageNotReadableException; // Import for malformed JSON

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatus(ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Validation failed"); // You could extract specific field errors here
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // --- NEW OR MODIFIED EXCEPTION HANDLERS ---

    // Handles cases where no suitable handler (controller method) is found for the request.
    // This typically results in a 404.
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Endpoint not found: " + e.getRequestURL());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // Returns 404
    }

    // Handles cases where a required @PathVariable is missing from the URL.
    // E.g., calling /play/ without matchId or player.
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Map<String, String>> handleMissingPathVariable(MissingPathVariableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Missing path variable: " + e.getVariableName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Returns 400
    }

    // Handles cases where a required @RequestParam is missing from the request.
    // E.g., calling /draw/{matchId}/{playerPathValue} but missing ?player=...
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingRequestParam(MissingServletRequestParameterException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Missing request parameter: " + e.getParameterName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Returns 400
    }

    // Handles cases where the request body is malformed JSON or cannot be read.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Malformed JSON request body");
        // You might want to log the full exception cause for debugging here
        // if (e.getCause() != null) {
        //     error.put("details", e.getCause().getMessage());
        // }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Returns 400
    }

    // This is your current generic exception handler
    // Keep it, but ensure more specific handlers come before it.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
        e.printStackTrace(); // Keep this for debugging, but consider more robust logging in production
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error: " + e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
