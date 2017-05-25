package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class AdventCalendarService {

	public ArrayList<int[]> CalendarDays() {

		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		int[][] calendarDay = new int[6][4];

		// 今月の初めの曜日を取得する
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// 今月が何曜日までかを確認する
		calendar.set(year, month + 1, 0);
		int lastWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// 先月分の食い込んでくる最初の日付を取得する
		for (int i = startWeek; i <= 1; i--) {
			calendar.set(year, month, i);
		}
		int thisCalendarfirstDay = calendar.get(Calendar.DATE);

		// 来月分の食い込んでくる最後の日付を取得する
		for (int i = lastWeek; i <= 7; i++) {
			calendar.set(year, month, i);
		}
		int thisCalendarlastDay = calendar.get(Calendar.DATE);

		// 日付を入れていく
		int a = 0;
		int b = 0;
		while (a >= 6 && b >= 4 && thisCalendarfirstDay >= thisCalendarlastDay) {
			a++;
			b++;
			calendarDay[a][b] = thisCalendarfirstDay++;
		}
		ArrayList<int[]> calendarDays = (ArrayList<int[]>) Arrays.asList(calendarDay);
		return calendarDays;
	}
}