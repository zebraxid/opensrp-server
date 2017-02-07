package org.opensrp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static Long getTimestampToday(){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        Date day = null;        
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        String today = dateFormat.format(now.getTime());
        try {
            day = dateFormat.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day.getTime();
	}
	
	public static Long getTimestamp(String day){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        Date date = null;        
        //String today = dateFormat.format(day);
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
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

