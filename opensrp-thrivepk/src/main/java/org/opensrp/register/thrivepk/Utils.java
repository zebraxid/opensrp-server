package org.opensrp.register.thrivepk;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.opensrp.common.util.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;

public class Utils {
	public static final String GLOBAL_DATE_FORMAT = "dd-MM-yyyy";
	public static final SimpleDateFormat GLOBAL_SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final String GLOBAL_DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
	public static final SimpleDateFormat GLOBAL_SDTF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	
	public static void createErrorResponse(String error, Map<String, Object> responseMap) {
		responseMap.put("ERROR", true);
		responseMap.put("ERROR_MESSAGE", error);
	}
	
	public static void createErrorResponse(String error, HttpServletResponse response) {
		try {
			response.getWriter().write(new JSONObject()
								.put("ERROR", true)
								.put("ERROR_MESSAGE", error).toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, Object> createMissingParamErrorResponse(String param, Map<String, Object> responseMap) {
		if(responseMap == null){
			responseMap = new HashMap<String, Object>();
		}
		responseMap.put("ERROR", true);
		responseMap.put("ERROR_MESSAGE", "No param named as '"+param+"' found");
		return responseMap;
	}
	
	public static Map<String, Object> createMissingSettingErrorResponse(String setting, Map<String, Object> responseMap) {
		if(responseMap == null){
			responseMap = new HashMap<String, Object>();
		}
		responseMap.put("ERROR", true);
		responseMap.put("ERROR_MESSAGE", "No setting named as '"+setting+"' configured");
		return responseMap;
	}
	
	public static void createResponse(HttpResponse response, Map<String, Object> responseMap) {
		try {
			Map<String, Object> mapResp = new Gson().fromJson(XML.toJSONObject(response.body()).getJSONObject("corpsms").toString(), new TypeToken<HashMap<String, Object>>() {}.getType());
		
			if(mapResp.get("response").toString().equalsIgnoreCase("ok")){
				responseMap.put("SUCCESS", true);
			}
			else {
				responseMap.put("ERROR", true);
				responseMap.put("ERROR_MESSAGE", mapResp.get("data"));
			}
			
			responseMap.putAll(mapResp);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
	
	public static String getStringFilter(String filter, HttpServletRequest req)
	{
	  return StringUtils.isEmptyOrWhitespaceOnly(req.getParameter(filter)) ? null : req.getParameter(filter);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Enum getEnumFilter(String filter, Class cls, HttpServletRequest req)
	{
	  String filterVal = getStringFilter(filter, req);
	  if (filterVal != null) {
	    return Enum.valueOf(cls, filterVal);
	  }
	  return null;
	}
	
	public static Boolean getBooleanFilter(String filter, HttpServletRequest req)
	{
	  String strval = getStringFilter(filter, req);
	  return strval == null ? null : Boolean.parseBoolean(strval);
	}
	
	public static Integer getIntegerFilter(String filter, HttpServletRequest req)
	{
	  String strval = getStringFilter(filter, req);
	  return strval == null ? null : Integer.parseInt(strval);
	}
	
	public static Float getFloatFilter(String filter, HttpServletRequest req)
	{
	  String strval = getStringFilter(filter, req);
	  return strval == null ? null : Float.parseFloat(strval);
	}
	
	public static Date getDateFilter(String filter, HttpServletRequest req) throws ParseException
	{
	  String strval = getStringFilter(filter, req);
	  return strval == null ? null : GLOBAL_SDF.parse(strval);
	}
	
	public static String setDateFilter(Date date) throws ParseException
	{
	  return date == null ? null : GLOBAL_SDF.format(date);
	}
	
	public static <T> void verifyRequiredProperties(List<String> properties, T entity) {
		if(properties != null)
		for (String p : properties) {
			Field[] aaa = entity.getClass().getDeclaredFields();
			for (Field field : aaa) {
				if(field.getName().equals(p)){
					field.setAccessible(true);
					try {
						if(field.get(entity) == null){
							throw new RuntimeException("A required field "+p+" was found empty");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						throw new RuntimeException("A required field "+p+" was not found in resource class");
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
