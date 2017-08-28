package com.example.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class Formatter {

	Calendar cal = Calendar.getInstance();

	public java.sql.Date parseSql(java.util.Date calendarMonth) {

		cal.setTime(calendarMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlCalendarMonth = new java.sql.Date(cal.getTimeInMillis());
		return sqlCalendarMonth;

	}

	public int parseDateToYear(java.util.Date calendarMonth) {

		SimpleDateFormat y = new SimpleDateFormat("yyyy");
		String strYear = y.format(calendarMonth);
		int year = Integer.parseInt(strYear);
		return year;

	}

	public int parseDateToMonth(java.util.Date calendarMonth) {

		SimpleDateFormat m = new SimpleDateFormat("MM");
		String strMonth = m.format(calendarMonth);
		int month = Integer.parseInt(strMonth);
		return month;
	}

	public Calendar getSettedCalendar(java.util.Date calendarMonth) {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		cal.setTime(calendarMonth);
		return cal;

	}
	/*
	 * public java.util.Date getSettedMonth(java.util.Date calendarMonth) {
	 * 
	 * Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST")); int
	 * year = parseDateToYear(calendarMonth); int month =
	 * parseDateToMonth(calendarMonth); cal.set(year, month, 1, 0, 0, 0);
	 * java.util.Date settedMonth = cal.getTime(); return settedMonth;
	 * 
	 * }
	 */
	// なにこれ
}
