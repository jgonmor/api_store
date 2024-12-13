package com.jgonmor.store.exceptions;

public class EmptyTableException extends RuntimeException {
    public EmptyTableException(String message) {
        super(message);
    }
}
