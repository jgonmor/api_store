package com.jgonmor.store.exceptions;

public class EmptyQueryException extends RuntimeException {
    public EmptyQueryException(String message) {
        super(message);
    }
}
