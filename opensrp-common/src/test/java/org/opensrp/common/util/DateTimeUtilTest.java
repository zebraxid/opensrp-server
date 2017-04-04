package org.opensrp.common.util;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateTimeUtilTest {

	@Test
	public void CconvertDateToTimestamp() {

		Long timeStamp = getTimestamp("2016-05-01");
		assertTrue("Previous (" + timeStamp
				+ ") should be less than current (" + System.currentTimeMillis()
				+ ")",System.currentTimeMillis() > timeStamp  );
	}

	public static Long getTimestamp(String day) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		try {
			date = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

}
