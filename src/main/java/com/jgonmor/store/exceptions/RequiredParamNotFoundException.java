package com.jgonmor.store.exceptions;

/**
 * RequiredParamNotFoundException Class
 * Thrown when a required param is missing.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
public class RequiredParamNotFoundException extends RuntimeException {
    public RequiredParamNotFoundException(String message) {
        super(message);
    }
}
