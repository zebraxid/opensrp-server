package org.opensrp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import static org.motechproject.util.DateUtil.inRange;

public class DateUtil {
    private static DateUtility dateUtility = new RealDate();
    static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat yyyyMMddTHHmmssSSSZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static void fakeIt(LocalDate fakeDayAsToday) {
        dateUtility = new MockDate(fakeDayAsToday);
    }

    public static LocalDate today() {
        return dateUtility.today();
    }

    public static long millis() {
        return dateUtility.millis();
    }

    public static boolean isDateWithinGivenPeriodBeforeToday(LocalDate referenceDateForSchedule, Period period) {
        return inRange(toTime(referenceDateForSchedule), toTime(today().minus(period)), toTime(today()));
    }

    private static DateTime toTime(LocalDate referenceDateForSchedule) {
        return referenceDateForSchedule.toDateTime(new LocalTime(0, 0));
    }
    /*
     * @param monthIndex 0 for January then increases accordingly 
     */
    public static List<Date> startAndEndDatesOfAllWeeksOfAMonth(int monthIndex){
    	List<Date> dates = new ArrayList<Date>();
    	Calendar now = GregorianCalendar.getInstance();
    	now.set(GregorianCalendar.MONTH, monthIndex);
    	int numberOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    	for(int i = 0; i < 5; i++){
    		
    	}
    	return null;
    }

    /**
     * Parses dates of following formats 
     * - yyyy-MM-dd
     * - yyyy-MM-dd HH:mm:ss
     * - yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @return
     * @throws ParseException 
     */
    public static Date parseDate(String date) throws ParseException{
    	try{
    		return yyyyMMdd.parse(date);
    	}
    	catch(ParseException e){}
    	try {
			return yyyyMMddHHmmss.parse(date);
		} catch (ParseException e) {}
		
    	return yyyyMMddTHHmmssSSSZ.parse(date);
    }
    
    public static LocalDate tryParse(String value, LocalDate defaultValue) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public static Date getDateFromString(String dateString)
    {
    	Date parsed = null;
	    try {
	    	if(dateString!=null && !dateString.equals("null") && dateString.length()>0)
	    	{
	    		parsed = sdf.parse(dateString.trim());	    	
	    	}
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return parsed;
    }
    
    public static List<String> getWeekBoundariesForDashboard(){   	
    	Calendar now = GregorianCalendar.getInstance();   	
    	
    	List<String> dates = new ArrayList<String>(); 
    	for(int monthIndex = 0; monthIndex < 4; monthIndex++){
    		now = GregorianCalendar.getInstance();
    		now.add(GregorianCalendar.MONTH, -monthIndex);
        	now.set(GregorianCalendar.DAY_OF_MONTH, 1);        	
        	int numOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);        	
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        	int numberOfWeeks = (int)Math.ceil( (double)numOfDaysInMonth/7);
        	System.out.println("current date- " + now.getTime() + " in a month with days- " + numOfDaysInMonth + " number of week- " + numberOfWeeks);
        	for(int i = 0; i< numberOfWeeks; i++){
        		int firstDay = i * 7 + 1;
        		int lastDay;
        		if(firstDay + 6 <= numOfDaysInMonth){
        			lastDay = firstDay + 6;
        		}
        		else{
        			lastDay = firstDay + numOfDaysInMonth % 7 -1;
        		}
        		now.set(GregorianCalendar.DAY_OF_MONTH, firstDay);
        		dates.add(dateFormatter.format(now.getTime()));
        		now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
        		dates.add(dateFormatter.format(now.getTime()));   
        	}
        	if(numberOfWeeks == 4){
        		dates.add("");
        		dates.add("");
        	}
    	}
    	
    	return dates;
    }
}

interface DateUtility {
    LocalDate today();

    long millis();
}

class RealDate implements DateUtility {
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public long millis() {
        return DateTime.now().getMillis();
    }
}

class MockDate implements DateUtility {
    private DateTime fakeDay;

    MockDate(LocalDate fakeDay) {
        this.fakeDay = fakeDay.toDateTimeAtStartOfDay();
    }

    @Override
    public LocalDate today() {
        return fakeDay.toLocalDate();
    }

    @Override
    public long millis() {
        return fakeDay.getMillis();
    }
}

