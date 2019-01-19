package com.pawpaw.framework.core.exception;

public class PawpawFrameworkException extends RuntimeException {

    public PawpawFrameworkException(String message) {
        super(message);
    }

    public PawpawFrameworkException(Throwable throwable) {
        super(throwable);
    }

}
