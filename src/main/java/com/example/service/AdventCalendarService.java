package com.example.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.domain.AdventCalendar;
import com.example.domain.Article;
import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

@Scope("prototype")
@Service
@Transactional
public class AdventCalendarService {

	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	Formatter format;
	@Autowired
	AdventCalendar adventCalendar;

	// static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
	// static int year = cal.get(Calendar.YEAR);
	// static int month = cal.get(Calendar.MONTH);
	SimpleDateFormat mm = new SimpleDateFormat("MM");

	public List<CalendarDay[]> generateCalendarDays(Date calendarMonth) {

		// もし渡された月のテーマがなかった場合

		java.sql.Date sqlCalendarMonth = format.parseSql(calendarMonth);
		Theme ifTheme = themeRepository.findByCalendarMonth(sqlCalendarMonth);
		List<Theme> nextThemelist = themeRepository.findByNextCalendarMonth(sqlCalendarMonth);
		List<Theme> backThemelist = themeRepository.findByBackCalendarMonth(sqlCalendarMonth);

		if (ifTheme == null) {

			if (nextThemelist.isEmpty() || backThemelist.isEmpty()) {

				// themelistがemptyならばcalendarMonthはそのまま

			} else {

				Theme nextTheme = nextThemelist.get(0);
				Theme backTheme = backThemelist.get(0);
				java.util.Date nextMonth = nextTheme.getCalendarMonth();
				java.util.Date backMonth = backTheme.getCalendarMonth();
				long nextTime = nextMonth.getTime();
				long backTime = backMonth.getTime();
				long nowTime = calendarMonth.getTime();
				long nextDiff = nextTime - nowTime;
				long backDiff = nowTime - backTime;

				if (nextDiff > backDiff) {

					calendarMonth = minMonth(calendarMonth);
					adventCalendar.setCalendarMonth(calendarMonth);

				} else if (nextDiff < backDiff) {

					calendarMonth = addMonth(calendarMonth);
					adventCalendar.setCalendarMonth(calendarMonth);

				} else if (nextDiff == backDiff) {

					calendarMonth = addMonth(calendarMonth);
					adventCalendar.setCalendarMonth(calendarMonth);

				}

			}

		}

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int year = format.parseDateToYear(calendarMonth);
		int month = format.parseDateToMonth(calendarMonth);
		cal.set(year, month - 1, 1, 0, 0, 0);

		List<CalendarDay[]> list = new ArrayList<CalendarDay[]>();

		// カレンダーの一番左上までカレンダーを引き戻す
		int firstweek = cal.get(Calendar.DAY_OF_WEEK);
		int subDay = 0;
		if (firstweek == 1) {
		} else if (firstweek >= 2) {

			while (firstweek > 1) {
				subDay--;
				firstweek--;
			}

		}

		cal.add(Calendar.DAY_OF_MONTH, subDay);

		// whileコントロール用のフラグ
		boolean flag = true;
		int count = 0;

		while (flag) {
			CalendarDay[] cld = new CalendarDay[7];
			// 一週間ごとにコンストラクタを呼び起こして、Listに足していく
			for (int i = 0; i <= 6; i++) {

				if (count == 0) {

					count++;
					CalendarDay calendarDay = new CalendarDay(calendarMonth, cal, themeRepository, articleRepository);
					cld[i] = calendarDay;

				} else {

					cal.add(Calendar.DAY_OF_MONTH, 1);
					CalendarDay calendarDay = new CalendarDay(calendarMonth, cal, themeRepository, articleRepository);
					cld[i] = calendarDay;

					if (i == 6) {
						list.add(cld);
					}
				}
			}

			// 35番目の部分になったら終了させていい可能性が出るので、チェックを行う。
			if (list.size() == 5) {

				// 35番目の日付
				int lastday = cal.get(Calendar.DAY_OF_MONTH);
				// その時の月
				int checkmonth = cal.get(Calendar.MONTH) + 1;
				// 31日がある月
				int[] intmonthlist = { 1, 3, 5, 7, 8, 10, 12 };
				// 30日までの月
				int[] intmonthlist2 = { 2, 4, 6, 9, 11 };

				// 値の中を調べるためにIntegerのListに変換する
				List<Integer> monthlist = new ArrayList<Integer>(intmonthlist.length);
				for (int j = 0; j < intmonthlist.length; j++) {
					monthlist.add(intmonthlist[j]);
				}

				List<Integer> monthlist2 = new ArrayList<Integer>(intmonthlist2.length);
				for (int k = 0; k < intmonthlist2.length; k++) {
					monthlist2.add(intmonthlist2[k]);
				}
				// lastdayが29日で2月でなければtrueのまま。
				if (lastday == 29 && checkmonth != 2) {

					flag = true;
					// lastdayが30日で、31日がある月であればtrueのまま。
				} else if (lastday == 30 && monthlist.contains(checkmonth)) {

					flag = true;
					// lastdayが29日で2月であればfalse。
				} else if (lastday == 29 && checkmonth == 2) {

					flag = false;

					// lastdayが30日で、31日がある月でなければfalse。
				} else if (lastday == 30 && monthlist2.contains(checkmonth)) {

					flag = false;

					// lastdayが少ない数字（いちお7未満とかで）か、31日であればfalse。
				} else if (lastday != 29 || lastday != 30 && lastday < 7 || lastday == 31) {

					flag = false;

				}

			}

			if (list.size() == 6) {

				flag = false;

			}

		}

		return list;

	}

	public List<CalendarDay[]> generateEditCalendar(Date calendarMonth) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		// calendarMonthの月にセット
		String streditYear = new SimpleDateFormat("yyyy").format(calendarMonth);
		int editYear = Integer.parseInt(streditYear);
		String streditMonth = mm.format(calendarMonth);
		int editMonth = Integer.parseInt(streditMonth);
		cal.set(editYear, editMonth - 1, 1, 0, 0, 0);

		List<CalendarDay[]> list = new ArrayList<CalendarDay[]>();

		// カレンダーの一番左上までカレンダーを引き戻す
		int firstweek = cal.get(Calendar.DAY_OF_WEEK);
		int subDay = 0;
		if (firstweek == 1) {
		} else if (firstweek >= 2) {

			while (firstweek > 1) {
				subDay--;
				firstweek--;
			}

		}

		cal.add(Calendar.DAY_OF_MONTH, subDay);

		// whileコントロール用のフラグ
		boolean flag = true;
		int count = 0;

		while (flag) {
			CalendarDay[] cld = new CalendarDay[7];
			// 一週間ごとにコンストラクタを呼び起こして、Listに足していく
			for (int i = 0; i <= 6; i++) {

				if (count == 0) {

					count++;
					CalendarDay calendarDay = new CalendarDay(calendarMonth, cal, themeRepository, articleRepository);
					cld[i] = calendarDay;

				} else {

					cal.add(Calendar.DAY_OF_MONTH, 1);
					CalendarDay calendarDay = new CalendarDay(calendarMonth, cal, themeRepository, articleRepository);
					cld[i] = calendarDay;

					if (i == 6) {
						list.add(cld);
					}
				}
			}

			// 35番目の部分になったら終了させていい可能性が出るので、チェックを行う。
			if (list.size() == 5) {

				// 35番目の日付
				int lastday = cal.get(Calendar.DAY_OF_MONTH);
				// その時の月
				int checkmonth = cal.get(Calendar.MONTH) + 1;
				// 31日がある月
				int[] intmonthlist = { 1, 3, 5, 7, 8, 10, 12 };
				// 30日までの月
				int[] intmonthlist2 = { 2, 4, 6, 9, 11 };

				// 値の中を調べるためにIntegerのListに変換する
				List<Integer> monthlist = new ArrayList<Integer>(intmonthlist.length);
				for (int j = 0; j < intmonthlist.length; j++) {
					monthlist.add(intmonthlist[j]);
				}

				List<Integer> monthlist2 = new ArrayList<Integer>(intmonthlist2.length);
				for (int k = 0; k < intmonthlist2.length; k++) {
					monthlist2.add(intmonthlist2[k]);
				}
				// lastdayが29日で2月でなければtrueのまま。
				if (lastday == 29 && checkmonth != 2) {

					flag = true;
					// lastdayが30日で、31日がある月であればtrueのまま。
				} else if (lastday == 30 && monthlist.contains(checkmonth)) {

					flag = true;
					// lastdayが29日で2月であればfalse。
				} else if (lastday == 29 && checkmonth == 2) {

					flag = false;

					// lastdayが30日で、31日がある月でなければfalse。
				} else if (lastday == 30 && monthlist2.contains(checkmonth)) {

					flag = false;

					// lastdayが少ない数字（いちお7未満とかで）か、31日であればfalse。
				} else if (lastday != 29 || lastday != 30 && lastday < 7 || lastday == 31) {

					flag = false;

				}

			}

			if (list.size() == 6) {

				flag = false;

			}

		}

		return list;

	}

	// themeとの連携用
	public java.sql.Date getCalendarMonth() {
		/*
		 * cal.set(year, month, 1, 0, 0, 0); Date calendarMonth = cal.getTime();
		 * cal.setTime(calendarMonth); cal.set(Calendar.HOUR_OF_DAY, 0);
		 * cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0);
		 * cal.set(Calendar.MILLISECOND, 0); java.sql.Date sqlCalendarMonth =
		 * new java.sql.Date(cal.getTimeInMillis()); return sqlCalendarMonth;
		 */
		java.util.Date calendarMonth = adventCalendar.getCalendarMonth();
		java.sql.Date sqlCalendarMonth = format.parseSql(calendarMonth);
		return sqlCalendarMonth;
	}

	// 次の月を確認して返す
	public Date addMonth(Date calendarMonth) {
		java.sql.Date sqlCalendarMonth = format.parseSql(calendarMonth);
		List<Theme> theme = themeRepository.findByNextCalendarMonth(sqlCalendarMonth);
		if (theme.isEmpty()) {
			return calendarMonth;
		} else {
			Theme nexttheme = theme.get(0);
			calendarMonth = nexttheme.getCalendarMonth();
			adventCalendar.setCalendarMonth(calendarMonth);
			return calendarMonth;
		}
		/*
		 * int calMM = format.parseDateToMonth(calendarMonth); int nextMM =
		 * format.parseDateToMonth(nextCalMonth); int i = 0;
		 * 
		 * if (nextMM < calMM) { i = (12 - calMM) + nextMM; } else if (nextMM ==
		 * calMM) { i = 12; } else { i = nextMM - calMM; }
		 * cal.add(Calendar.MONTH, i); java.util.Date afterMonth =
		 * cal.getTime(); java.sql.Date afterCalendarMonth =
		 * format.parseSql(afterMonth); Theme aftertheme =
		 * themeRepository.findByCalendarMonth(afterCalendarMonth); while
		 * (aftertheme == null) { month = month - 12; } List<CalendarDay[]>
		 * gencaldays = generateCalendarDays(); return gencaldays;
		 */
	}

	// 前の月を確認して返す
	public java.util.Date minMonth(Date calendarMonth) {
		java.sql.Date sqlCalendarMonth = format.parseSql(calendarMonth);
		List<Theme> theme = themeRepository.findByBackCalendarMonth(sqlCalendarMonth);
		if (theme.isEmpty()) {
			return calendarMonth;
		} else {
			Theme backtheme = theme.get(0);
			calendarMonth = backtheme.getCalendarMonth();
			adventCalendar.setCalendarMonth(calendarMonth);
			return calendarMonth;
			/*
			 * String strCalendarMonth = mm.format(calendarMonth); String
			 * strNextCalMonth = mm.format(backcalmonth); int calMM =
			 * Integer.parseInt(strCalendarMonth); int backMM =
			 * Integer.parseInt(strNextCalMonth); int i = 0; if (calMM < backMM)
			 * { i = calMM + (12 - backMM); } else if (calMM == backMM) { i =
			 * 12; } else { i = calMM - backMM; } month = month - i;
			 * java.sql.Date afterCalendarMonth = getCalendarMonth(); Theme
			 * aftertheme =
			 * themeRepository.findByCalendarMonth(afterCalendarMonth); while
			 * (aftertheme == null) { month = month - 12; } List<CalendarDay[]>
			 * gencaldays = generateCalendarDays(); return gencaldays;
			 */
		}
	}
}