package com.example.service;

import java.util.Date;
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

	// 記事を全件取得
	public List<Article> findAll() {
		return articleRepository.findAllOrderById();
	}

	// 一件のみ記事を取得
	public Article findOne(Integer id) {
		return articleRepository.findOne(id);
	}

	// カレンダーの日付から記事を取得
	public Article findByCalendardate(Date calendardate) {
		return articleRepository.findByCalendardate(calendardate);
	}

	// 記事の作成
	public Article create(Article article) {
		return articleRepository.save(article);
	}

	// 記事の更新
	public Article update(Article article) {
		return articleRepository.save(article);
	}

	// 記事の削除
	public void delete(Integer id) {
		articleRepository.delete(id);
	}

}
