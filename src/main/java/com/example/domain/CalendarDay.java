package com.example.domain;

import java.sql.Date;
import java.sql.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Component
public class CalendarDay{
	@Autowired
	private ThemeRepository themeRepository;
	@Autowired
	private ArticleRepository articleRepository;
	
	// カレンダーの年月日
	java.util.Date calendarMonth;
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
	
	public CalendarDay(java.util.Date calendarMonth,Calendar calendarDate){
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(calendarMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlcalendarMonth = new java.sql.Date(cal.getTimeInMillis());
		getArticle(calendarDate);
		isAvalable(calendarDate);
		isEnabled(sqlcalendarMonth,calendarDate);
		isRegistered(calendarDate);
	}
		
	// Articleを取得
	@Bean
	public Article getArticle(Calendar calendarDate) {
		Article article = articleRepository.findByCalendarDate(calendarDate);
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
	public boolean isEnabled(java.sql.Date sqlcalendarMonth,Calendar calendarDate) {
		theme = themeRepository.findByCalendarMonth(sqlcalendarMonth);
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