package com.example.domain;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

public class CalendarDay {
	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ThemeRepository themeRepository;

	private int calendarMonth;
	private Calendar calendarDate;
	private int[] enabledDates;
	private int onlyDate = calendarDate.get(Calendar.DATE);
	private int onlyMonth = calendarDate.get(Calendar.MONTH);

	public CalendarDay(int calendarMonth, Calendar calendarDate) {
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
	}

	public Article getArticle() {
		Article article = articleRepository.findOneByCalendarDate(calendarDate);
		return article;
	}

	// 今月であればtrue
	public boolean isAvalable() {
		if (calendarMonth == onlyMonth) {
			return true;
		} else {
			return false;
		}
	}

	// 許可された日であればtrue
	public boolean isEnabled() {
		if (Arrays.asList(enabledDates).contains(onlyDate)) {
			return true;
		} else {
			return false;
		}
	}

	// articleに登録された日付であれば記事を取得できる。
	public boolean isRegistered() {
		if (getArticle() != null) {
			return true;
		} else {
			return false;
		}
	}
}
