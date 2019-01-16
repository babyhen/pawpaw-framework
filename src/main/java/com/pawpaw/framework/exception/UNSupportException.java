package com.pawpaw.framework.exception;

import com.pawpaw.common.exception.PawpawException;

public class UNSupportException extends PawpawException {


    public UNSupportException(String message) {

        super(message);
    }

    public UNSupportException(Throwable t) {
        super(t);
    }
}
