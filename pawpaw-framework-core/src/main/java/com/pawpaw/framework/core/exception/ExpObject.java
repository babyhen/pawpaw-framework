package com.pawpaw.framework.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ExpObject {
    private Object expSource;
    private Exception exception;

    public ExpObject(Exception exception) {
        this(null, exception);
    }

    public ExpObject(Object expSource, Exception exception) {
        this.expSource = expSource;
        this.exception = exception;
    }
}
