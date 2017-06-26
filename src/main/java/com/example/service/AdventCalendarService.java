package com.example.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.repository.ThemeRepository;

@Service
@Transactional
public class AdventCalendarService {

	@Autowired
	ThemeRepository themeRepository;

	public List<CalendarDay> generateCalendarDays(Theme theme) {

		List<CalendarDay> cld = new ArrayList();

		Calendar calendar = Calendar.getInstance();

		int calendarMonth = theme.getCalendarmonth();
		int[] enableddates = theme.getEnableddates();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		CalendarDay calendarDay = new CalendarDay(month, calendar);

		/* 今月が何曜日から開始されているか確認する */
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);

		/* 先月が何日までだったかを確認する */
		calendar.set(year, month, 0);
		int beforeMonthlastDay = calendar.get(Calendar.DATE);

		/* 今月が何日までかを確認する */
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);

		/* 先月分の日付を格納する */
		for (int i = startWeek - 2; i >= 0; i--) {
			cld.add(beforeMonthlastDay - i, calendarDay);
		}

		/* 今月分の日付を格納する */
		for (int i = 1; i <= thisMonthlastDay; i++) {
			cld.add(i, calendarDay);
		}

		/* 翌月分の日付を格納する */
		int nextMonthDay = 1;
		while (cld.size() % 7 != 0) {
			cld.add(nextMonthDay++, calendarDay);
		}

		return cld;
	}
}