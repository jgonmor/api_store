package com.jgonmor.store.controller;

import com.jgonmor.store.exceptions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles when a table is empty.
     *
     * @return http status 404.
     */
    @ExceptionHandler(EmptyTableException.class)
    public ResponseEntity<ErrorResponse> handleEmptyTableException(EmptyTableException ex) {
       return ResponseEntity.status(404)
                      .body(ErrorResponse.create(ex,
                                                 HttpStatus.NOT_FOUND,
                                                 ex.getMessage()));
    }

    /**
     * Handles when a query response is empty.
     *
     * @return http status 404.
     */
    @ExceptionHandler(EmptyQueryException.class)
    public ResponseEntity<ErrorResponse> handleEmptyQueryException(EmptyQueryException ex) {
        return ResponseEntity.status(404)
                             .body(ErrorResponse.create(ex,
                                                        HttpStatus.NOT_FOUND,
                                                        ex.getMessage()));
    }

    /**
     * Handles when entry is not found.
     *
     * @return http status 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                             .body(ErrorResponse.create(ex,
                                                        HttpStatus.NOT_FOUND,
                                                        ex.getMessage()));
    }

    /**
     * Handles when a required param is missing.
     *
     * @return http status 400.
     */
    @ExceptionHandler(RequiredParamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRequiredParamNotFoundException(RequiredParamNotFoundException ex) {
        return ResponseEntity.status(400)
                             .body(ErrorResponse.create(ex,
                                                        HttpStatus.BAD_REQUEST,
                                                        ex.getMessage()));
    }

    /**
     * Handles when a product has not enough stock.
     *
     * @return http status 404.
     */
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughStockException(NotEnoughStockException ex) {
        return ResponseEntity.status(404)
                             .body(ErrorResponse.create(ex,
                                                        HttpStatus.NOT_FOUND,
                                                        ex.getMessage()));
    }
}
