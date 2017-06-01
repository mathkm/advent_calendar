package com.example.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AdventCalendarServicePre {

	Calendar tmp = Calendar.getInstance(TimeZone.getTimeZone("JST"));tmp.set(2017,4,1,0,0,0);

	int subDay = tmp.get(Calendar.DAY_OF_WEEK) - 7;if(subDay<0)
	{
		tmp.add(Calendar.DAY_OF_MONTH, subDay);
	}

	SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy/MM/dd");formatter.setTimeZone(TimeZone.getTimeZone("JST"));System.out.println(formatter.format(tmp.getTime()));

}
