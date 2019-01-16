package com.pawpaw.framework.common.concurrent.lock;

import com.pawpaw.common.RedisOperater;
import com.pawpaw.common.concurrent.lock.RedisDistributeLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SingleNodeDistributeLock extends RedisDistributeLock {
	private static final Logger logger = LoggerFactory.getLogger(com.pawpaw.common.concurrent.lock.SingleNodeDistributeLock.class);

	private static final String DEFAULT_KEY_ROOT_PATH = "pawpawtech:lock:singlenode:";
	private JedisPool jedisPool;
	private RedisOperater redisOperator;
	private String resourceIdentifier;
	private String key;
	private String value;

	private long lockExpireTime;

	//////////////////////// construct//////////////////////
	/**
	 * construct method use default lock expire time
	 * 
	 * @param redisNode
	 * @param resourceIdentifier
	 */
	public SingleNodeDistributeLock(JedisPool jedisPool, String resourceIdentifier) {
		this(jedisPool, resourceIdentifier, 3000);
	}

	public SingleNodeDistributeLock(JedisPool jedisPool, String resourceIdentifier, long lockExpireTime) {
		this.jedisPool = jedisPool;
		if (this.jedisPool == null) {
			throw new RuntimeException("jedis pool can't be null");
		}
		//
		this.redisOperator = new RedisOperater(this.jedisPool);
		//
		this.resourceIdentifier = resourceIdentifier;
		if (this.resourceIdentifier == null) {
			throw new RuntimeException("resource identifier can't be empty");
		}
		this.key = DEFAULT_KEY_ROOT_PATH + resourceIdentifier;
		this.value = UUID.randomUUID().toString();
		//
		if (lockExpireTime <= 0) {
			throw new RuntimeException("lock expire time must greater than zero!");
		}
		this.lockExpireTime = lockExpireTime;
	}

	/////////////////////////////////////////////////////////////////////

	@Override
	public boolean tryLock() {
		logger.debug("begin lock resource {},redis key is {} , value is {}", this.resourceIdentifier, this.key,
				this.value);
		String lockResult = this.redisOperator.set(key, value, "NX", "PX", this.lockExpireTime);
		logger.debug("resource {} lock result is {}", this.resourceIdentifier, lockResult);
		if (!StringUtils.equalsIgnoreCase("OK", lockResult)) {
			logger.warn("resource {} lock fail...", this.resourceIdentifier);
			return false;
		}
		logger.debug("resource {} lock successful,key is {},value is {}", this.resourceIdentifier, this.key,
				this.value);
		return true;
	}

	@Override
	public void unlock() {
		logger.debug("begin unlock resource {},redis key is {} , value is {}", this.resourceIdentifier, this.key,
				this.value);
		List<String> keys = new ArrayList<>(1);
		keys.add(this.key);
		List<String> args = new ArrayList<>(1);
		args.add(this.value);
		Object result = this.redisOperator.eval(this.getUnlockScript().toString(), keys, args);
		if (result == null) {
			logger.error("unlock resource {} error,cause unlock result returned by redis is null");
			return;
		}
		logger.debug("unlock resource {} result is {}", this.resourceIdentifier, result.toString());
		if ("0".equals(result.toString())) {
			logger.warn("unlock resource {} fail...", this.resourceIdentifier);
		} else if ("1".equals(result.toString())) {
			logger.debug("unlock resource {} successful...", this.resourceIdentifier);
		} else {
			throw new RuntimeException("invalid unlock result " + result.toString());
		}
	}

	///////////////////////////////////////////////////////////////////////

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
	///////////////////////////////// getter and setter///////////////////////
}
