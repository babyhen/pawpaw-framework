package com.pawpaw.framework.core.common.concurrent;

import com.pawpaw.framework.core.common.util.CollectionConventer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发执行的任务执行器，当所有现成都执行完毕的时候，才会进行主线程
 *
 * @Auther: liujixin
 * @Date: 2018-12-25
 */

public class ConcurrentExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentExecutor.class);

    private ExecutorService executor;


    public ConcurrentExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public <V> ConcurrentExecuteResult<V> execute(Collection<ConcurrentCall<V>> calls) {
        return execute(calls, 0L);

    }

    /**
     * 并发执行子任务，所有任务都返回后，返回响应
     *
     * @param calls
     * @param timeOut in millionseconds
     *                timeout in millions the main thread blocks.
     */
    public <V> ConcurrentExecuteResult<V> execute(Collection<? extends ConcurrentCall<V>> calls, long timeOut) {
        logger.debug("开始多线程任务，子任务数量:{},time out is {}", calls.size(), timeOut);
        if (timeOut < 0) {
            throw new IllegalArgumentException("time out must greate or equals than zero");
        }
        ConcurrentExecuteResult<V> result = new ConcurrentExecuteResult<V>(calls.size());
        if (calls == null || calls.size() == 0) {
            return result;
        }
        CountDownLatch cdl = new CountDownLatch(calls.size());
        List<Future<V>> futures = new LinkedList<>();
        for (ConcurrentCall<V> call : calls) {
            call.setCdl(cdl);
            Future<V> future = executor.submit(call);
            futures.add(future);
        }
        try {
            logger.debug("wait sub thread finish...");
            boolean finishBeforeTimeElipse = true;
            if (timeOut == 0) {
                cdl.await();
            } else {
                finishBeforeTimeElipse = cdl.await(timeOut, TimeUnit.MILLISECONDS);
            }
            result.setMainThreadWaitTimeOut(!finishBeforeTimeElipse);
            logger.debug("all sub thread finish...begin main thread");
            for (Future<V> f : futures) {
                try {
                    result.addCompleteCallResult(f.get(timeOut, TimeUnit.MILLISECONDS));
                } catch (ExecutionException | TimeoutException e) {
                    result.addFailCallResult(e);
                }
            }
            return result;
        } catch (InterruptedException e) {
            logger.warn("error happends on main thread,{}", e);
            throw new RuntimeException(e);
        }

    }

    public <V> ConcurrentExecuteResult<V> executeBatch(Collection<? extends ConcurrentCall<V>> calls, int batchSize) {
        return executeBatch(calls, batchSize, 0L);
    }

    /**
     * calls will be seperated into a few of batch. batchs are executed
     * sequentially.
     *
     * @param calls
     * @param batchSize
     * @return results
     */
    public <V> ConcurrentExecuteResult<V> executeBatch(Collection<? extends ConcurrentCall<V>> calls, int batchSize,
                                                       long timeOut) {
        logger.debug("begin concurrent calls，all size:{},batch size:{}", calls.size(), batchSize);
        if (calls == null || calls.size() == 0) {
            return new ConcurrentExecuteResult<V>(calls.size());
        }
        ConcurrentExecuteResult<V> finalResult = new ConcurrentExecuteResult<>(calls.size());
        List<List<? extends ConcurrentCall<V>>> deviceCalls = CollectionConventer.devide(calls, batchSize);
        for (List<? extends ConcurrentCall<V>> batch : deviceCalls) {
            ConcurrentExecuteResult<V> batchResult = execute(batch, timeOut);
            finalResult.merge(batchResult);
        }
        return finalResult;
    }

    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

}
