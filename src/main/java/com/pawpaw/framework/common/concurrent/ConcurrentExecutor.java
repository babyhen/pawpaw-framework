package com.pawpaw.framework.common.concurrent;

import com.pawpaw.common.concurrent.ConcurrentCall;
import com.pawpaw.common.concurrent.ConcurrentExecuteResult;
import com.pawpaw.common.util.CollectionConventer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * concurrent executor,the main thread continues while all child (call or
 * thread) finish or fail(exception happens)
 */
public class ConcurrentExecutor {

	private static final Logger logger = LoggerFactory.getLogger(com.pawpaw.common.concurrent.ConcurrentExecutor.class);

	private static final ExecutorService executor = Executors.newCachedThreadPool();

	public static <V> ConcurrentExecuteResult<V> execute(Collection<ConcurrentCall<V>> calls) {
		return execute(calls, 0L);

	}

	/**
	 * execute calls parallelly.
	 *
	 * @param calls
	 * @param timeOut  in millionseconds
	 *            timeout in millions the main thread blocks.
	 */
	public static <V> ConcurrentExecuteResult<V> execute(Collection<ConcurrentCall<V>> calls, long timeOut) {
		logger.debug("开始多线程任务，子任务数量:{},time out is {}", calls.size(), timeOut);
		if (timeOut < 0) {
			throw new InvalidParameterException("time out must greate or equals than zero");
		}
		ConcurrentExecuteResult<V> result = new ConcurrentExecuteResult<V>();
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
			if (timeOut == 0) {
				cdl.await();
			} else {
				cdl.await(timeOut, TimeUnit.MILLISECONDS);
			}
			logger.debug("all sub thread finish...begin main thread");
			for (Future<V> f : futures) {
				try {
					result.addCompleteResult(f.get(timeOut, TimeUnit.MILLISECONDS));
				} catch (ExecutionException | TimeoutException e) {
					result.addException(e);
				}
			}
			return result;
		} catch (InterruptedException e) {
			logger.warn("error happends on main thread,{}", e);
			throw new RuntimeException(e);
		}

	}

	public static <V> ConcurrentExecuteResult<V> executeBatch(Collection<ConcurrentCall<V>> calls, int batchSize) {
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
	public static <V> ConcurrentExecuteResult<V> executeBatch(Collection<ConcurrentCall<V>> calls, int batchSize,
                                                              long timeOut) {
		logger.debug("begin concurrent calls，all size:{},batch size:{}", calls.size(), batchSize);
		if (calls == null || calls.size() == 0) {
			return new ConcurrentExecuteResult<V>();
		}
		ConcurrentExecuteResult<V> finalResult = new ConcurrentExecuteResult<>();
		List<List<ConcurrentCall<V>>> deviceCalls = CollectionConventer.devide(calls, batchSize);
		for (List<ConcurrentCall<V>> batch : deviceCalls) {
			ConcurrentExecuteResult<V> batchResult = execute(batch, timeOut);
			finalResult.merge(batchResult);
		}
		return finalResult;
	}

	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////

}
