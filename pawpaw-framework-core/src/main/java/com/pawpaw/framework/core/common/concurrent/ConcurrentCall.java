package com.pawpaw.framework.core.common.concurrent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


public abstract class ConcurrentCall<V> implements Callable<V> {
    Logger logger = LoggerFactory.getLogger(ConcurrentCall.class);
    private CountDownLatch cdl;

    protected void setCdl(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public V call() throws Exception {
        try {
            V v = this.doCall();
            return v;
        } catch (Throwable t) {
            logger.error("exception happends,{}", t.getMessage());
            throw new RuntimeException(t);
        } finally {
            this.cdl.countDown();
        }

    }

    public abstract V doCall();

}