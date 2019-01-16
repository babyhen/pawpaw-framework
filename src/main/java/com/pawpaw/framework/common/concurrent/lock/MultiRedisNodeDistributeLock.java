package com.pawpaw.framework.common.concurrent.lock;

import com.pawpaw.common.RedisOperater;
import com.pawpaw.common.concurrent.ConcurrentCall;
import com.pawpaw.common.concurrent.ConcurrentExecuteResult;
import com.pawpaw.common.concurrent.ConcurrentExecutor;
import com.pawpaw.common.concurrent.lock.RedisDistributeLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * a distribute lock implemented by redis
 * 
 * @author liujixin
 *
 */
public class MultiRedisNodeDistributeLock extends RedisDistributeLock {
	private static final Logger logger = LoggerFactory.getLogger(com.pawpaw.common.concurrent.lock.MultiRedisNodeDistributeLock.class);

	public static final String DEFAULT_KEY_ROOT_PATH = "pawpawtech:lock:multinode:";
	private Collection<JedisPool> jedisPools;
	private String resourceIdentifier;
	private String key;
	private String value;
	private long lockExpireTime;

	////////////////////////// construct///////////////////////////////////////
	public MultiRedisNodeDistributeLock(Collection<JedisPool> jedisPools, String resourceIdentifier) {
		this(jedisPools, resourceIdentifier, 3000);
	}

	public MultiRedisNodeDistributeLock(Collection<JedisPool> jedisPools, String resourceIdentifier,
			long lockExpireTime) {
		this.jedisPools = jedisPools;
		if (this.jedisPools == null || this.jedisPools.size() < 3) {
			throw new RuntimeException("jedis pool size must large than 3");
		}
		//
		if (resourceIdentifier == null) {
			throw new RuntimeException("resource identifier can't be empty");
		}
		this.resourceIdentifier = resourceIdentifier;
		//
		if (lockExpireTime <= 0) {
			throw new RuntimeException("lock expire time must greater than zero!");
		}
		this.lockExpireTime = lockExpireTime;
		//
		this.key = DEFAULT_KEY_ROOT_PATH + resourceIdentifier;
		this.value = UUID.randomUUID().toString();
	}

	//////////////////////////////////////////////////////////////
	@Override
	public boolean tryLock() {
		logger.debug("begin lock resource {},redis key is {} , value is {}", this.resourceIdentifier, this.key,
				this.value);
		long startTime = System.currentTimeMillis();
		// get lock from all the redis nodes
		List<ConcurrentCall<Boolean>> calls = new ArrayList<>(this.jedisPools.size());
		this.jedisPools.forEach(e -> {
			calls.add(new ConcurrentCall<Boolean>() {

				@Override
				public Boolean doCall() {
					logger.debug("begin lock resource {} on sub node ,redis key is {} , value is {}",
							resourceIdentifier, key, value);
					RedisOperater redisOperator = new RedisOperater(e);
					String lockResult = redisOperator.set(key, value, "NX", "PX", lockExpireTime);
					logger.debug("resource {} lock result is {}", resourceIdentifier, lockResult);
					if (!StringUtils.equalsIgnoreCase("OK", lockResult)) {
						logger.warn("resource {} lock fail...", resourceIdentifier);
						return false;
					}
					logger.debug("resource {} lock successful,key is {},value is {}", resourceIdentifier, key, value);
					return true;
				}
			});
		});
		// use a time out in case of block all sub lock task
		ConcurrentExecuteResult<Boolean> lockResult = ConcurrentExecutor.execute(calls, 50);
		// time spent on get the locks
		long timeForLockRedisNodes = System.currentTimeMillis() - startTime;
		logger.debug("time spent on lock all redis nodes {}", timeForLockRedisNodes);
		// lock succ count
		List<Boolean> completeResult = lockResult.getCompleteResults();
		int lockSuccSize = completeResult.stream().filter(e -> e).collect(Collectors.toList()).size();
		// If and only if the client was able to acquire the lock in the
		// majority of the instances (at least 3), and the total time elapsed to
		// acquire the lock is less than lock validity time, the lock is
		// considered to be acquired.
		int totalNode = this.jedisPools.size();
		int majoritySize = (totalNode - totalNode % 2) / 2 + 1;
		long lockTimeRemain = lockExpireTime - timeForLockRedisNodes;
		if (lockTimeRemain > 0 && lockSuccSize >= majoritySize) {
			logger.debug("lock resource {} successful, lock time remain {},redis key is {} , value is {}",
					resourceIdentifier, lockTimeRemain, key, value);
			return true;
		} else {
			logger.debug("lock resource {} fail, lock time remain {},locked node size is {} ", resourceIdentifier,
					lockTimeRemain, lockSuccSize);
			return false;
		}
	}

	@Override
	public void unlock() {
		logger.debug("begin unlock resource {},redis key is {} , value is {}", this.resourceIdentifier, this.key,
				this.value);

		List<ConcurrentCall<Boolean>> calls = new ArrayList<>();
		this.jedisPools.forEach(e -> {
			calls.add(new ConcurrentCall<Boolean>() {

				@Override
				public Boolean doCall() {
					List<String> keys = new ArrayList<>(1);
					keys.add(key);
					List<String> args = new ArrayList<>(1);
					args.add(value);
					RedisOperater redisOperator = new RedisOperater(e);
					Object result = redisOperator.eval(getUnlockScript(), keys, args);
					if (result == null) {
						logger.error(
								"unlock resource {} child node error,cause unlock result returned by redis is null");
						return false;
					}
					logger.debug("unlock resource {} child node result is {}", resourceIdentifier, result.toString());
					if ("0".equals(result.toString())) {
						logger.warn("unlock resource {} child node fail...", resourceIdentifier);
						return false;
					} else if ("1".equals(result.toString())) {
						logger.debug("unlock resource {} child node successful...", resourceIdentifier);
						return true;
					} else {
						throw new RuntimeException("invalid unlock result " + result.toString());
					}
				}
			});
		});
		ConcurrentExecuteResult<Boolean> finalResult = ConcurrentExecutor.execute(calls, 100);
		logger.debug("unlock resource {} final result {}", this.resourceIdentifier, finalResult);

	}

	//////////////////// getter and setter////////////////////////
	public static String getDefaultKeyRootPath() {
		return DEFAULT_KEY_ROOT_PATH;
	}

	@Override
	public void lock() {
		while (!this.tryLock()) {

		}
	}

	@Override
	public boolean tryLock(long millionSecond) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < millionSecond) {
			boolean isSucc = this.tryLock();
			if (isSucc) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}
}
