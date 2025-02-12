package com.jgonmor.store.exceptions;

/**
 * ResourceNotFoundException Class
 * Thrown when an entry is not found.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
