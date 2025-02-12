package com.jgonmor.store.exceptions;

/**
 * EmptyQueryException Class
 * Thrown when a query response is empty.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class EmptyQueryException extends RuntimeException {
    public EmptyQueryException(String message) {
        super(message);
    }
}
