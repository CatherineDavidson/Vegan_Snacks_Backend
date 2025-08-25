package com.examly.springapp.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }
}
