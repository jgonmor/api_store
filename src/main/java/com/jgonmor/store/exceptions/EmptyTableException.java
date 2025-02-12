package com.jgonmor.store.exceptions;

/**
 * EmptyTableException Class
 * Thrown when a table is empty.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class EmptyTableException extends RuntimeException {
    public EmptyTableException(String message) {
        super(message);
    }
}
