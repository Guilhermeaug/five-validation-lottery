package com.fivevalidation.lottery.advice;

import com.fivevalidation.lottery.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A Rest Controller Advice that handle expections shared by all controllers
 */
@RestControllerAdvice
public class GlobalAdvice {

    /**
     * Handle validations errors thrown by the @Valid annotation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map((FieldError fieldError) -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        HashMap<String, List<String>> errorsMap = new HashMap<>();
        errorsMap.put("errors", errors);
        return new ResponseEntity<>(errorsMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Fired when a resource requested is not available
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        HashMap<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error", ex.getMessage());
        return new ResponseEntity<>(errorsMap, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Fired when a @RequestBody does not meet the requirements
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        HashMap<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error", ex.getMessage());
        return new ResponseEntity<>(errorsMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
