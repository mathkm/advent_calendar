package com.example.domain;

import java.util.Date;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Configurable
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CalendarDay{
	@Autowired
	private ThemeRepository themeRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	// カレンダーの年月日
	int calendarMonth;
	// articleの日付
	Calendar calendarDate; //  = Calendar.getInstance(TimeZone.getTimeZone("JST"));
	int[] enabledDates;
	String themeYearmonth;
	// 有効日付と同じなのか確認するためのフォーマッター
	SimpleDateFormat dd = new SimpleDateFormat("dd");
	// 同じ年月なのか確認するためyyyy-MMにするフォーマッター
	SimpleDateFormat yyyymm = new SimpleDateFormat("yyyy-MM");
	Theme theme;
	Date articleDate;
	String articleDay;
	String articleYearmonth;
	
	public CalendarDay(int calendarMonth, Calendar calendarDate){
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
		ThemeRepository themeRepository = this.themeRepository;
		Theme theme = themeRepository.findByCalendarMonth(calendarMonth);
		enabledDates = theme.getEnabledDates();
		articleDate = calendarDate.getTime();
		themeYearmonth = yyyymm.format(calendarMonth);
		articleDay = dd.format(articleDate);
		articleYearmonth = yyyymm.format(articleDate);
	}
	CalendarDay cld = new CalendarDay(calendarMonth,calendarDate);
	Theme themee = themeRepository.findByCalendarMonth(cld.calendarMonth);
	
	// Articleを取得
	public Article getArticle() {
		Article article = articleRepository.findByCalendarDate(calendarDate);
		return article;
	}

	// テーマと同じ年・月であればtrue
	public boolean isAvalable() {
		if (articleYearmonth == themeYearmonth) {
			return true;
		} else {
			return false;
		}
	}

	// テーマで許可されている日に存在していればtrue
	public boolean isEnabled() {
		if (Arrays.asList(enabledDates).contains(articleDay)) {
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