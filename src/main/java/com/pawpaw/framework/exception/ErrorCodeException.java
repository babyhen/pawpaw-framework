package com.pawpaw.framework.exception;

public class ErrorCodeException extends PawpawFrameworkException {

    private int code;

    public ErrorCodeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCodeException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }
}
