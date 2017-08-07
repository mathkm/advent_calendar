package com.example.service;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public Article findByCalendarDate(Date calendarDate) {
		return articleRepository.findByCalendarDate(calendarDate);
	}

	public Article create(Article article) {
		return articleRepository.save(article);
	}

	public int update(String title, String url, Date calendarDate) {
		return articleRepository.updateByCalendarDate(title, url, calendarDate);
	}

	public void delete(java.sql.Date calendarDate) {
		articleRepository.deleteByCalendarDate(calendarDate);
	}

	public void deleteOneMonth(java.sql.Date calendarMonth, java.sql.Date lastDay) {
		articleRepository.deleteByCalendarMonth(calendarMonth, lastDay);
	}

}
