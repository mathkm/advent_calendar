package com.example.domain;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;
import com.example.service.AdventCalendarService;
import com.example.service.ArticleService;
import com.example.service.ThemeService;

@Component
@Scope("prototype")
public class CalendarDay{
	//@Qualifier("com.example.service.ThemeService")
	//@Autowired
	private ThemeService themeService;
	//@Qualifier("com.example.service.ArticleService")
	//@Autowired
	private ArticleService articleService;
	
	// カレンダーの年月日
	public Date calendarMonth;
	// articleの日付
	Calendar calendarDate;	
	Theme theme;
	int[] enabledDates;
	java.util.Date articleDate;
	String themeYearmonth;
	String articleDay;
	String articleYearmonth;
	SimpleDateFormat dd;
	SimpleDateFormat yyyymm;
	
	@Autowired
	public CalendarDay(Date calendarMonth,Calendar calendarDate){
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
		getArticle(calendarDate);
		isAvalable(calendarDate);
		isEnabled(calendarMonth,calendarDate);
		isRegistered(calendarDate);
	}
		
	// Articleを取得
	public Article getArticle(Calendar calendarDate) {
		Article article = articleService.findByCalendarDate(calendarDate);
		return article;
	}

	// テーマと同じ年・月であればtrue
	public boolean isAvalable(Calendar calendarDate) {
		articleDate = calendarDate.getTime();
		// 有効日付と同じなのか確認するためのフォーマッター
		dd = new SimpleDateFormat("dd");
		// 同じ年月なのか確認するためyyyy-MMにするフォーマッター
		yyyymm = new SimpleDateFormat("yyyyMM");
		themeYearmonth = yyyymm.format(calendarMonth);
		articleDay = dd.format(articleDate);
		articleYearmonth = yyyymm.format(articleDate);
		if (articleYearmonth == themeYearmonth) {
			return true;
		} else {
			return false;
		}
	}

	// テーマで許可されている日に存在していればtrue
	public boolean isEnabled(Date calendarMonth,Calendar calendarDate) {
		theme = themeService.findByCalendarMonth(calendarMonth);
		enabledDates = theme.getEnabledDates();
		articleDay = dd.format(articleDate);
		if (Arrays.asList(enabledDates).contains(articleDay)) {
			return true;
		} else {
			return false;
		}
	}

	// articleに登録された日付であれば記事を取得できる。
	public boolean isRegistered(Calendar calendarDate) {
		if (getArticle(calendarDate) != null) {
			return true;
		} else {
			return false;
		}
	}
}