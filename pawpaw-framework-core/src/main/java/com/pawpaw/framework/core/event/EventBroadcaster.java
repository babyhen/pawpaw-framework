package com.pawpaw.framework.core.event;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 应该是单例的
 */
public class EventBroadcaster {

    private static volatile EventBroadcaster instance;

    public static EventBroadcaster getInstance() {
        if (instance == null) {
            synchronized (EventBroadcaster.class) {
                if (instance == null) {
                    instance = new EventBroadcaster();
                }
            }
        }
        return instance;
    }

    private final List<IEventListener> listeners;

    //是否异步
    @Getter
    private volatile boolean async;
    private volatile ExecutorService asyncThreadPool;

    /**
     *
     */
    private EventBroadcaster() {
        this.listeners = new LinkedList<>();
        this.configAsync(true);
    }

    /**
     * 设置同步执行还是异步执行
     *
     * @param value2Set
     */

    public synchronized void configAsync(boolean value2Set) {
        boolean oldV = this.async;
        if (value2Set) {    //如果设置成异步
            if (oldV) {     //如果原来就是异步，啥也不做
                //nothing to do
            } else {     //如果原来是同步，要变成异步。那么初始化股票池
                this.asyncThreadPool = Executors.newFixedThreadPool(2);
                this.async = value2Set;   //状态等线程池初始化之后再设置，防止别的线程失败
            }
        } else {     //如果要设置成同步
            if (!oldV) {   //如果原来就是同步，啥也不做
                //nothing to do
            } else {        //如果原来是异步。把股票池停掉。以节省系统资源
                this.async = value2Set;   //状态要先设置，让新的任务走同步，防止线程池shutdown期间没法提交任务
                this.asyncThreadPool.shutdown();
                this.asyncThreadPool = null;   //释放引用
            }
        }
    }

    public void regist(IEventListener listener) {
        listeners.add(listener);
    }

    public void broadcastEvent(IEvent event) {
        //
        Runnable run = new Runnable() {
            @Override
            public void run() {
                boolean handled = false;
                for (IEventListener listener : EventBroadcaster.this.listeners) {
                    boolean canHandle = listener.canHandle(event);
                    if (canHandle) {
                        listener.onEvent(event);
                        handled = true;
                    }

                }
                //没有能够处理的，默认打印到控制台
                if (!handled && event != null) {
                    System.out.println("没有listener可以处理此event:" + event.desc());
                }
            }
        };
        //
        if (this.async) {
            this.asyncThreadPool.execute(run);
        } else {
            run.run();
        }


    }
}
