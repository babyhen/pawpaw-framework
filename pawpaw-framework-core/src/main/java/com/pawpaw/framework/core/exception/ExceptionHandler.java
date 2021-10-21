package com.pawpaw.framework.core.exception;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @see ExceptionListener
 */
public class ExceptionHandler {
    private List<ExceptionListener> listeners;

    public static ExceptionHandler defaultHandler() {
        ExceptionHandler handler = new ExceptionHandler();
        handler.regist(new ConsolePrintExceptionListener());
        return handler;
    }


    public ExceptionHandler() {
        this.listeners = new CopyOnWriteArrayList<>();
    }

    public void regist(ExceptionListener listener) {
        this.listeners.add(listener);
    }

    public void broadcastException(ExpObject e) {
        for (ExceptionListener listener : listeners) {
            listener.onException(e);
        }
    }


}


