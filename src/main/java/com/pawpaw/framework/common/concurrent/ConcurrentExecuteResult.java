package com.pawpaw.framework.common.concurrent;


import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author liujixin
 * @date 2018-12-28
 */

public class ConcurrentExecuteResult<V> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConcurrentExecuteResult.class);


    private final int callSize;
    private CopyOnWriteArrayList<V> completeCallResults;
    private CopyOnWriteArrayList<Exception> failCallResult;
    private AtomicBoolean mainThreadWaitTimeOut = new AtomicBoolean(false);

    public ConcurrentExecuteResult(int callSize) {
        this(callSize, new ArrayList<V>(0), new ArrayList<Exception>(0));
    }

    public ConcurrentExecuteResult(int callSize, Collection<V> completeResults, Collection<Exception> exceptions) {
        if (callSize < 0) {
            throw new IllegalArgumentException("call size must be a positive number");
        }
        this.callSize = callSize;
        if (completeResults == null) {
            completeResults = new ArrayList<>(0);
        }
        this.completeCallResults = new CopyOnWriteArrayList<>(completeResults);
        if (exceptions == null) {
            exceptions = new ArrayList<Exception>(0);
        }
        this.failCallResult = new CopyOnWriteArrayList<>(exceptions);
    }

    /**
     * 获取任务的最终执行状态
     */
    public ConcurrentResultStatus getStatus() {
        if (this.mainThreadWaitTimeOut.get()) {
            return ConcurrentResultStatus.MAIN_THREAD_WAIT_TIME_OUT;
        }
        if (this.completeCallResults.size() == this.callSize) {
            return ConcurrentResultStatus.ALL_SUB_CALL_SUCC;
        }
        if (this.failCallResult.size() == this.callSize) {
            return ConcurrentResultStatus.ALL_SUB_CALL_FAIL;
        }
        if (this.completeCallResults.size() > 0) {
            return ConcurrentResultStatus.PART_OF_SUB_CALL_SUCC;
        }
        LOGGER.error("illegal state ,call size:{},complete size:{},fail size:{},wait time out:{}", callSize, this.completeCallResults.size(), this.failCallResult.size(), this.mainThreadWaitTimeOut);
        throw new IllegalStateException("ConcurrentExecuteResult的状态非法，");

    }

    protected void addCompleteCallResult(V v) {
        this.completeCallResults.add(v);
    }

    protected void addFailCallResult(Exception e) {
        this.failCallResult.add(e);
    }


    /**
     * 合并结果集，如果target超时，则该对象也设置成超时
     */
    protected void merge(ConcurrentExecuteResult<V> target) {
        if (target.getCompleteCallResults() != null) {
            this.completeCallResults.addAll(target.getCompleteCallResults());
        }
        if (target.getFailCallResult() != null) {
            this.failCallResult.addAll(target.getFailCallResult());
        }

        this.mainThreadWaitTimeOut.compareAndSet(false, target.mainThreadWaitTimeOut.get());

    }

    @Override
    public String toString() {
        return "succ=" + this.completeCallResults.toString() + " ; fail=" + this.failCallResult.toString();
    }

    public CopyOnWriteArrayList<V> getCompleteCallResults() {
        return completeCallResults;
    }

    protected void setCompleteCallResults(CopyOnWriteArrayList<V> completeCallResults) {
        this.completeCallResults = completeCallResults;
    }

    public CopyOnWriteArrayList<Exception> getFailCallResult() {
        return failCallResult;
    }

    protected void setFailCallResult(CopyOnWriteArrayList<Exception> failCallResult) {
        this.failCallResult = failCallResult;
    }


    protected void setMainThreadWaitTimeOut(boolean mainThreadTimeOut) {
        this.mainThreadWaitTimeOut.set(mainThreadTimeOut);
    }

    /**
     * 执行结果的状态，调用者可以通过此来判断最后的执行结果
     */
    public static enum ConcurrentResultStatus {
        //所有的子任务都成功
        ALL_SUB_CALL_SUCC,
        //部分子任务成功
        PART_OF_SUB_CALL_SUCC,
        //所有子任务都失败
        ALL_SUB_CALL_FAIL,
        //主线程等待超时，子线程的完成结果未知
        MAIN_THREAD_WAIT_TIME_OUT;
    }
}