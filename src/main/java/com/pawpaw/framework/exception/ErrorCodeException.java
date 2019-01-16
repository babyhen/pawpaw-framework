package com.pawpaw.framework.exception;

import com.pawpaw.common.exception.PawpawException;

public class ErrorCodeException extends PawpawException {

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
