package org.opensrp.web.rest;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;
import org.joda.time.DateTime;

import com.mysql.jdbc.StringUtils;

public class RestUtils {
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
	public static final SimpleDateFormat SDTF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	
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
	
	public static DateTime getDateFilter(String filter, HttpServletRequest req) throws ParseException
	{
	  String strval = getStringFilter(filter, req);
	  return strval == null ? null : new DateTime(strval);
	}
	
	public static DateTime[] getDateRangeFilter(String filter, HttpServletRequest req) throws ParseException
	{
	  String strval = getStringFilter(filter, req);
	  if(strval == null){
		  return null;
	  }
	  DateTime d1 = new DateTime(strval.substring(0, strval.indexOf(":")));
	  DateTime d2 = new DateTime(strval.substring(strval.indexOf(":")+1));
	  return new DateTime[]{d1,d2};
	}
	
	public static String setDateFilter(Date date) throws ParseException
	{
	  return date == null ? null : SDF.format(date);
	}
	
	public static <T> void verifyRequiredProperties(List<String> properties, T entity) {
		if(properties != null)
		for (String p : properties) {
			Field[] aaa = entity.getClass().getDeclaredFields();
			for (Field field : aaa) {
				if(field.getName().equals(p)){
					field.setAccessible(true);
					try {
						if(field.get(entity) == null || field.get(entity).toString().trim().equalsIgnoreCase("")){
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
	
	@SuppressWarnings("serial")
	public static Map<String, Query> parseClauses(final Query qry, Map<String, Query> clauses) {
		if(clauses == null){
			clauses = new HashMap<>();
		}
		
		if(qry instanceof BooleanQuery){
			for (BooleanClause clause : ((BooleanQuery)qry).clauses()) {
				if(clause.toString().startsWith("+") == false){
					throw new UnsupportedOperationException("Only AND operations are supported for NON FTS searches");
				}
				parseClauses(clause.getQuery(), clauses);				
			}
		}
		else if(qry instanceof TermRangeQuery){
			clauses.put(((TermRangeQuery)qry).getField(), qry);
		}
		else if(qry instanceof TermQuery){
			clauses.put(((TermQuery)qry).getTerm().field(), qry);
		}
		else {
			throw new UnsupportedOperationException("Only BooleanQuery, TermRangeQuery and TermQuery is supported for NON FTS searches. You sent a type "+qry.getClass().getName());				
		}
		
		return clauses;
	}
	
	public static Map<String, Query> parseClauses(String query) {
		StandardQueryParser queryParser = new StandardQueryParser();
		Query qry;
		try {
			qry = queryParser.parse(query, null);
		} catch (QueryNodeException e) {
			throw new IllegalArgumentException(e);
		}

		return parseClauses(qry, null);
	}
	
	public static void main(String[] args) {
		System.out.println(getUpperDateFilter(parseClauses("lastEdited:[1 TO 1491056284492]").get("lastEdited")));
	}
	
	private static String getLowerRange(Query query) {
		return ((TermRangeQuery)query).getLowerTerm().utf8ToString();
	}
	
	private static String getUpperRange(Query query) {
		return ((TermRangeQuery)query).getUpperTerm().utf8ToString();
	}
	
	private static DateTime strToDate(String date) {
		if(NumberUtils.isDigits(date)){
			return new DateTime(Long.parseLong(date));
		}
		return DateTime.parse(date);
	}
	
	public static DateTime getLowerDateFilter(Query query) {
		if(query == null){
			return null;
		}
		return strToDate(getLowerRange(query));
	}
	
	public static DateTime getUpperDateFilter(Query query) {
		if(query == null){
			return null;
		}
		return strToDate(getUpperRange(query));
	}
	
	public static String getStringFilter(Query query) {
		if(query == null){
			return null;
		}
		return ((TermQuery) query).getTerm().text();
	}
}
