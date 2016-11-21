package org.opensrp.connector.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Component
public class RedisServiceImpl implements RedisService {
	@Autowired
	JedisPool jedisPool;

	private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class.toString());

	/**
	 * Set the respective fields to the respective values. HMSET replaces old
	 * values with new values. If key does not exist, a new key holding a hash
	 * is created. Return OK or Exception if hash is empty
	 */
	@Override
	public String saveData(String key, Map<String, String> data) {
		try {
			Jedis jedis = getJedis();
			if (key == null || key.isEmpty()) {
				return "userid cannot be null or empty";
			}
			if (data == null || data.isEmpty()) {
				return "user data is required";
			}
			return jedis.hmset(key, data);
		} catch (Exception e) {
			logger.error("", e);
			return "Exception occurred";
		}
	}

	private Jedis getJedis() {
		Jedis redis = null;
		try {
			redis = jedisPool.getResource();

		} catch (JedisConnectionException e) {
			if (redis != null) {
				jedisPool.returnBrokenResource(redis);
				redis = null;
			}
			logger.error("", e);
		} finally {
			if (redis != null) {
				jedisPool.returnResource(redis);
			}
		}
		return redis;
	}

	/**
	 * Remove the specified keys. If a given key does not exist no operation is
	 * performed for this key. The command returns the number of keys removed
	 */
	@Override
	public Long deleteData(String key) {
		try {
			Jedis jedis = getJedis();
			if (key == null || key.isEmpty()) {
				return 0l;
			}
			return jedis.del(key);
		} catch (Exception e) {
			logger.error("", e);
			return 0l;
		}
	}

	@Override
	public Map<String, String> getData(String key) {
		try {
			Jedis jedis = getJedis();
			if (key == null || key.isEmpty()) {
				return null;
			}

			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

}