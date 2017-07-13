package com.example.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;
	
	public List<Article> findAll(){
		return articleRepository.findAll();
	}
	
	public Article findByCalendarDate(Calendar calendarDate){
		return articleRepository.findByCalendarDate(calendarDate);
	}
}
