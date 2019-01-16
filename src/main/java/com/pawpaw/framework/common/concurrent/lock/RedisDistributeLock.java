package com.pawpaw.framework.common.concurrent.lock;

import com.pawpaw.common.concurrent.lock.DistributeLock;
import com.pawpaw.common.concurrent.lock.SingleNodeDistributeLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a distribute lock implemented by redis
 * 
 * @author liujixin
 *
 */
public abstract class RedisDistributeLock implements DistributeLock {
	private static final Logger logger = LoggerFactory.getLogger(com.pawpaw.common.concurrent.lock.RedisDistributeLock.class);
	private static StringBuffer unlockScript;

	protected String getUnlockScript() {
		if (unlockScript == null) {
			initUnlockScript();
		}
		return unlockScript.toString();
	}

	/**
	 * init unlock script. this script is used by redis command "eval"
	 */
	private void initUnlockScript() {
		if (unlockScript == null) {
			synchronized (SingleNodeDistributeLock.class) {
				if (unlockScript == null) {
					unlockScript = new StringBuffer();
					unlockScript.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] then ");
					unlockScript.append("return redis.call(\"del\",KEYS[1]) ");
					unlockScript.append("	else ");
					unlockScript.append("	 return 0 ");
					unlockScript.append("	 end ");
					logger.debug("use redis script:{}", unlockScript.toString());
				}
			}
		}

	}
}
