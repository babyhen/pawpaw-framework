package com.pawpaw.framework.core.common.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;


/**
 * 异步的执行任务
 *
 * @Auther: liujixin
 * @Date: 2018-12-25
 */
public class AsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(AsyncExecutor.class);

    private ExecutorService executor;

    public AsyncExecutor(ExecutorService executor) {
        this.executor = executor;
    }


    public void execute(AsyncCal call) {

        this.executor.execute(new Runnable() {

            @Override
            public void run() {
                if (call == null) {
                    return;
                }

                try {
                    long start = System.currentTimeMillis();
                    call.call();
                    long spend = System.currentTimeMillis() - start;
                    logger.debug("async task spend time {}", spend);

                } catch (Exception e) {
                    call.onException(e);
                }

            }
        });

    }

    public interface AsyncCal {

        public void call();

        public void onException(Exception e);
    }

}
