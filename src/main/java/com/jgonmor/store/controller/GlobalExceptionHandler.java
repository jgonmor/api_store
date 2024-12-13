package com.jgonmor.store.controller;

import com.jgonmor.store.exceptions.EmptyTableException;

import com.jgonmor.store.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyTableException.class)
    public ResponseEntity<ErrorResponse> handleEmptyTableException(EmptyTableException ex) {
       return ResponseEntity.status(404)
                      .body(ErrorResponse.create(ex,
                                                 HttpStatus.NOT_FOUND,
                                                 ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                             .body(ErrorResponse.create(ex,
                                                        HttpStatus.NOT_FOUND,
                                                        ex.getMessage()));
    }
}
