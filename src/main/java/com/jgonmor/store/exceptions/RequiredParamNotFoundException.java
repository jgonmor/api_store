package com.jgonmor.store.exceptions;

public class RequiredParamNotFoundException extends RuntimeException {
    public RequiredParamNotFoundException(String message) {
        super(message);
    }
}
