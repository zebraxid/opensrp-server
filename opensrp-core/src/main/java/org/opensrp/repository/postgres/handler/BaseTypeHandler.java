package org.opensrp.repository.postgres.handler;

import org.joda.time.DateTime;
import org.opensrp.util.DateTimeTypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseTypeHandler {
	
	public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
}
