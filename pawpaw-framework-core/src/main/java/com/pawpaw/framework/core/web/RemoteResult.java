package com.pawpaw.framework.core.web;

import java.beans.Transient;

public class RemoteResult<T> {
    private RemoteResultCode code;
    private String message;
    private T data;


    public RemoteResult() {
        this(null);
    }

    public RemoteResult(T data) {
        this(RemoteResultCode.SUCC, data);
    }

    public RemoteResult(RemoteResultCode code, T data) {
        this(code, code.getDesc(), data);
    }

    public RemoteResult(RemoteResultCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Transient
    public boolean isSucc() {
        return this.code == RemoteResultCode.SUCC;
    }
    ////////////////////////////////////////////////////////////


    public RemoteResultCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
