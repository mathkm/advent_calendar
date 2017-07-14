package com.example.domain;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class CalendarDay{
	
	// カレンダーの年月日
	Date calendarMonth;
	// articleの日付
	Calendar calendarDate;	
	// テーマリポジトリ
	ThemeRepository themeRepository;
	// アーティクルリポジトリ
	ArticleRepository articleRepository;
	// Theme型定義
	Theme theme;
	// DBからStringで取得したものをどうにかするんだよ？
	String inlineStrEnabledDates;
	String[] strEnabledDates;
	int[] enabledDates;
	// 記事の日付
	Date articleDate;
	// sqlに送る用ふたつ
	java.sql.Date sqlCalendarMonth;
	java.sql.Date sqlArticleDate;
	// 以下は比較と整形用
	String strThemeYearMonth;
	String strArticleDay;
	String strArticleYearMonth;
	int articleDay;
	int articleYearMonth;
	int themeYearMonth;
	SimpleDateFormat dd;
	SimpleDateFormat yyyymm;
	
	public CalendarDay(Date calendarMonth,Calendar calendarDate,
			ThemeRepository themeRepository,ArticleRepository articleRepository){
		this.calendarMonth = calendarMonth;
		this.calendarDate = calendarDate;
		this.themeRepository = themeRepository;
		this.articleRepository = articleRepository;
		// 有効日付と同じなのか確認するためのフォーマッター
		dd = new SimpleDateFormat("dd");
		// 同じ年月なのか確認するためyyyy-MMにするフォーマッター
		yyyymm = new SimpleDateFormat("yyyyMM");
		// 記事の日付をDate型で取り出す
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		cal.setTime(calendarMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		sqlCalendarMonth = new java.sql.Date(cal.getTimeInMillis());
    	articleDate = calendarDate.getTime();
		cal.setTime(articleDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		sqlArticleDate = new java.sql.Date(cal.getTimeInMillis());
		getArticle(sqlArticleDate,articleRepository);
		isAvalable(sqlArticleDate,yyyymm);
		isEnabled(sqlCalendarMonth,sqlArticleDate,themeRepository,dd);
		isRegistered(sqlArticleDate,articleRepository);
	}
		
	// Articleを取得
	public Article getArticle(java.sql.Date sqlArticleDate,ArticleRepository articleRepository){
		Article article = articleRepository.findByCalendarDate(sqlArticleDate);
		if(article == null){
			return null;
		}else{
			return article;
		}
	}

	// テーマと同じ年・月であればtrue
	public boolean isAvalable(java.sql.Date sqlArticleDate,SimpleDateFormat yyyymm) {
		strThemeYearMonth = yyyymm.format(calendarMonth);
		strArticleYearMonth = yyyymm.format(sqlArticleDate);
		themeYearMonth = Integer.parseInt(strThemeYearMonth);
		articleYearMonth = Integer.parseInt(strArticleYearMonth);
		if (articleYearMonth == themeYearMonth) {
			return true;
		} else {
			return false;
		}
	}

	// テーマで許可されている日に存在していればtrue
	public boolean isEnabled(java.sql.Date sqlCalendarMonth,java.sql.Date sqlArticleDate,ThemeRepository themeRepository,SimpleDateFormat dd) {
		theme = themeRepository.findByCalendarMonth(sqlCalendarMonth);
		inlineStrEnabledDates = theme.getEnabledDates();
		strEnabledDates = inlineStrEnabledDates.split(",");
		strArticleDay = dd.format(sqlArticleDate);
		articleDay = Integer.parseInt(strArticleDay);
		enabledDates = new int[strEnabledDates.length];
		
		for (int i = 0; i < strEnabledDates.length; i++) {
			try{
		       enabledDates[i] = Integer.parseInt(strEnabledDates[i]);
			}catch (NumberFormatException e) {
		           e.printStackTrace(); // -5aのとき、スタックトレースが出力される
		       }
		    }
		
		List<int[]> list = Arrays.asList(enabledDates);
		
		if (list.contains(articleDay) == true) {
			return true;
		} else {
			return false;
		}
	}

	// articleに登録された日付であれば記事を取得できる。
	public boolean isRegistered(java.sql.Date sqlArticleDate,ArticleRepository articleRepository) {
		if (getArticle(sqlArticleDate,articleRepository) != null) {
			return true;
		} else {
			return false;
		}
	}
}