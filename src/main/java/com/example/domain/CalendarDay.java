package com.example.domain;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

import lombok.Data;

public class CalendarDay {
	@Autowired
	ThemeRepository themeRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	// カレンダーの年月日
	int calendarMonth;
	
	// articleの日付
	Calendar calendarDate; //  = Calendar.getInstance(TimeZone.getTimeZone("JST"));
	
	// calendarMonthの年月のカレンダーを引っ張ってくる
	Theme theme;
	// theme月の有効日付を取得する
	int[] enabledDates;
	// 記事の日付をDate型で取り出す
	Date articleDate;
	// 記事の日付から日付を抽出
	String articleDay;
	// 記事の日付から年月を抽出
	String articleYearmonth;
	// テーマの日付か年月を抽出
	String themeYearmonth;
	
	// コンストラクタ
	public CalendarDay(int calendarMonth, Calendar calendarDate) {
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
		// 有効日付と同じなのか確認するためのフォーマッター
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		// 同じ年月なのか確認するためyyyy-MMにするフォーマッター
		SimpleDateFormat yyyymm = new SimpleDateFormat("yyyy-MM");
		themeYearmonth = yyyymm.format(calendarMonth);
		/*
		 * AutowiredされているはずのThemeRepositoryがnullになっていることが問題点。
		 * コンストラクタでAutowiredさせ、newして呼び出してみる。
		 */
		theme = themeRepository.findByCalendarMonth(calendarMonth);
		enabledDates = theme.getEnabledDates();
		Date articleDate = calendarDate.getTime();
		articleDay = dd.format(articleDate);
		articleYearmonth = yyyymm.format(articleDate);
	}
	
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
