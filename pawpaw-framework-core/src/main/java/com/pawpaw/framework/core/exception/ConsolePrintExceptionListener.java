package com.pawpaw.framework.core.exception;

public class ConsolePrintExceptionListener implements ExceptionListener {

    @Override
    public void onException(ExpObject e) {
        System.out.println(e);
    }
}