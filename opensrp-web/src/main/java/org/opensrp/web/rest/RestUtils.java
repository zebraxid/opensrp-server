package org.opensrp.web.rest;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.mysql.jdbc.StringUtils;

public class RestUtils {
	
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	
	public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
	
	public static final SimpleDateFormat SDTF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	public static String getStringFilter(String filter, HttpServletRequest req) {
		return StringUtils.isEmptyOrWhitespaceOnly(req.getParameter(filter)) ? null : req.getParameter(filter);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Enum getEnumFilter(String filter, Class cls, HttpServletRequest req) {
		String filterVal = getStringFilter(filter, req);
		if (filterVal != null) {
			return Enum.valueOf(cls, filterVal);
		}
		return null;
	}
	
	public static Integer getIntegerFilter(String filter, HttpServletRequest req) {
		String strval = getStringFilter(filter, req);
		return strval == null ? null : Integer.parseInt(strval);
	}
	
	public static Float getFloatFilter(String filter, HttpServletRequest req) {
		String strval = getStringFilter(filter, req);
		return strval == null ? null : Float.parseFloat(strval);
	}
	
	public static DateTime getDateFilter(String filter, HttpServletRequest req) throws ParseException {
		String strval = getStringFilter(filter, req);
		return strval == null ? null : new DateTime(strval);
	}
	
	public static DateTime[] getDateRangeFilter(String filter, HttpServletRequest req) throws ParseException {
		String strval = getStringFilter(filter, req);
		if (strval == null) {
			return null;
		}
		DateTime d1 = new DateTime(strval.substring(0, strval.indexOf(":")));
		DateTime d2 = new DateTime(strval.substring(strval.indexOf(":") + 1));
		return new DateTime[] { d1, d2 };
	}
	
	public static void main(String[] args) {
		System.out.println(new DateTime("​1458932400000"));
	}
	
	public static String setDateFilter(Date date) throws ParseException {
		return date == null ? null : SDF.format(date);
	}
	
	public static <T> void verifyRequiredProperties(List<String> properties, T entity) {
		if (properties != null)
			for (String p : properties) {
				Field[] aaa = entity.getClass().getDeclaredFields();
				for (Field field : aaa) {
					if (field.getName().equals(p)) {
						field.setAccessible(true);
						try {
							if (field.get(entity) == null || field.get(entity).toString().trim().equalsIgnoreCase("")) {
								throw new RuntimeException("A required field " + p + " was found empty");
							}
						}
						catch (IllegalArgumentException e) {
							e.printStackTrace();
							throw new RuntimeException("A required field " + p + " was not found in resource class");
						}
						catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
	}
	
	public static enum ANCVISIT {
		ANC1("ANC Reminder Visit 1"), ANC2("ANC Reminder Visit 2"), ANC3("ANC Reminder Visit 3"), ANC4(
		        "ANC Reminder Visit 4");
		
		String value;
		
		private ANCVISIT(String s) {
			value = s;
		}
		
		public String getAncVisitValue() {
			return value;
		}
		
		private static final Map<String, ANCVISIT> lookup = new HashMap<String, ANCVISIT>();
		static {
			// Create reverse lookup hash map
			for (ANCVISIT d : ANCVISIT.values())
				lookup.put(d.getAncVisitValue(), d);
		}
		
		public static ANCVISIT get(String value) {
			// the reverse lookup by simply getting
			// the value from the lookup HashMap.
			return lookup.get(value);
		}
		
		public static String getValue(String name) {
			// the reverse lookup by simply getting
			// the value from the lookup HashMap.
			for (Entry<String, ANCVISIT> entry : lookup.entrySet()) {
				if (name.equalsIgnoreCase(entry.getValue().toString())) {
					return entry.getKey();
				}
			}
			return null;
		}
		
		public static List<String> names() {
			
			List<String> list = new ArrayList<String>();
			for (ANCVISIT s : ANCVISIT.values()) {
				list.add(s.name());
			}
			
			return list;
		}
	}
	
	public static enum ANCVISITDUEDAY {
		ANC1(56), ANC2(168), ANC3(224), ANC4(252);
		
		int value;
		
		private ANCVISITDUEDAY(int days) {
			value = days;
		}
		
		public int getAncVisitDueDayValue() {
			return value;
		}
		
		private static final Map<String, Integer> lookup = new HashMap<String, Integer>();
		static {
			// Create reverse lookup hash map
			for (ANCVISITDUEDAY d : ANCVISITDUEDAY.values())
				lookup.put(d.toString(), d.getAncVisitDueDayValue());
		}
		
		public static int get(String key) {
			// the reverse lookup by simply getting
			// the value from the lookup HashMap.
			return lookup.get(key);
		}
	}
	
	public static enum ENTITYTYPES {
		MCAREMOTHER, MCARECHILD;
	}
	
	public static enum CONCEPTS {
		SYSTOLIC("5085AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), DIASTOLIC("5086AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), TEMPERATURE("5088AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), PULSE("5087AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
		WEIGHT("5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), PALLOR("5245AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), SWELLING("163894AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), BATHCANALBLEEDING("147232AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
		JAUNDICE("136443AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), CONVULSIONS("206AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), LIVEBIRTH(""), STILLBIRTH(""), MISCARRIAGE(""), 
		CHILDNAME("1586AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), CHILDGENDER("1587AAAAAAAAAAAAAAAAAAAAAAAAAAAA"), MALEGENDER("1534AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), FEMALEGENDER("1535AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
		UNKNOWNGENDER("1067AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), OTHERGENDER("5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), DATEOFCONFINEMENT("5599AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		String value;
		
		private CONCEPTS(String conceptValue) {
			value = conceptValue;
		}
	}
}
