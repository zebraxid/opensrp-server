package org.opensrp.connector.services;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.connector.SpringApplicationContextProvider;
import org.opensrp.connector.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;;

public class RedisServiceTest extends SpringApplicationContextProvider {
	@Autowired
	RedisService redisService;

	Map<String, String> data = new HashMap<String, String>();

	String key;
	String idKey = "id";
	String anc1Key = "anc1";

	@Before
	public void setup() {
		data.put(idKey, "" + 1001);
		data.put(anc1Key, "2016-11-10");
		key = "testuser";
	}

	@After
	public void tearDown() {
		data.clear();
		key = null;
		redisService.deleteData(key);
	}

	@Test
	public void saveDataFailsIfKeyIsNull() throws Exception {
		String response = redisService.saveData(null, data);
		Assert.assertEquals(response, "userid cannot be null or empty");
	}

	@Test
	public void saveDataFailsIfKeyIsEmpty() throws Exception {
		data.clear();
		String response = redisService.saveData(key, data);
		Assert.assertEquals(response, "user data is required");

	}

	@Test
	public void saveDataFailsIfDataIsNull() throws Exception {
		String response = redisService.saveData(key, null);
		Assert.assertEquals(response, "user data is required");

	}

	@Test
	public void saveDataFailsIfDataIsEmpty() throws Exception {
		data.clear();
		String response = redisService.saveData(key, data);
		Assert.assertEquals(response, "user data is required");

	}

	@Test
	public void shouldSaveDataToRedisAndReturnStringResponse() throws Exception {
		String response = redisService.saveData(key, data);
		Assert.assertEquals(response, "OK");

	}

	@Test
	public void getDataShouldFailIfKeyIsNull() throws Exception {
		Map<String, String> response = redisService.getData(null);
		Assert.assertEquals(response, null);
	}

	@Test
	public void getDataShouldFailIfKeyIsEmpty() throws Exception {
		Map<String, String> response = redisService.getData("");
		Assert.assertEquals(response, null);
	}

	@Test
	public void shouldGetDataFromRedis() throws Exception {
		redisService.saveData(key, data);
		Map<String, String> response = redisService.getData(key);
		Assert.assertTrue(response.containsKey(idKey));
		Assert.assertEquals(response.get(idKey), "" + 1001);
	}

	@Test
	public void deleteDataShouldFailIfKeyIsNull() throws Exception {
		Long response = redisService.deleteData(null);
		Assert.assertEquals(response.longValue(), 0l);
	}

	@Test
	public void deleteDataShouldFailIfKeyIsEmpty() throws Exception {
		Long response = redisService.deleteData("");
		Assert.assertEquals(response.longValue(), 0l);
	}

	@Test
	public void shouldDeleteDataFromRedis() throws Exception {
		Long response = redisService.deleteData(key);
		Assert.assertEquals(response.longValue(), 1l);
	}

}
