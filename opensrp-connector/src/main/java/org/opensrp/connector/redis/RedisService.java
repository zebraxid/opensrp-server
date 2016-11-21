package org.opensrp.connector.redis;

import java.util.Map;

public interface RedisService {

	String saveData(String key, Map<String, String> data);

	Long deleteData(String key);

	Map<String, String> getData(String key);

}