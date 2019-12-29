package com.pawpaw.framework.core.common.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujixin
 * @date 2018-12-29
 */
public class PawpawThreadFactory implements ThreadFactory {
    private static final String DEFAULT_NAME_PREFIX = "pawpaw-thread-";
    private String namePrefix;
    private AtomicInteger threadCounter;

    public PawpawThreadFactory() {
        this(DEFAULT_NAME_PREFIX);
    }

    public PawpawThreadFactory(String namePrefix) {
        if (namePrefix != null) {
            this.namePrefix = namePrefix;
        } else {
            this.namePrefix = DEFAULT_NAME_PREFIX;
        }

        this.threadCounter = new AtomicInteger(0);

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        int threadNum = this.threadCounter.incrementAndGet();
        t.setName(this.namePrefix + threadNum);
        return t;
    }
}
