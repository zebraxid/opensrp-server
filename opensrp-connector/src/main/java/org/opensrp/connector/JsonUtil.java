package org.opensrp.connector;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	public static String getValue(JSONObject object, String field, String defaultV) {
		try {
			if(object.has(field) && object.isNull(field) == false && object.getString(field).equalsIgnoreCase("null") == false){					
				return object.getString(field);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return defaultV;
	}
	public static String getValue(JSONObject object, String field) {
		return getValue(object, field, null);
	}
	
	public static DateTime getDateValue(JSONObject object, String field) {
		String v = getValue(object, field, null);
		if(v != null){
			return DateTime.parse(v);
		}
		return null;
	}
}
