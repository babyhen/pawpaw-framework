package com.pawpaw.framework.common.concurrent.lock;

/**
 * a distribute lock implemented by redis
 * 
 * @author liujixin
 *
 */
public interface DistributeLock {
	/**
	 * try to get the lock
	 */
	public boolean tryLock();

	/**
	 * block until specified millions elapsed
	 * 
	 * @param millions
	 */
	public boolean tryLock(long millionSecond);

	/**
	 * get the lock
	 */
	public void lock();

	/**
	 * release the lock
	 */
	public void unlock();

}
