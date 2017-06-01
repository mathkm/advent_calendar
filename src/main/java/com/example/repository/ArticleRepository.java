package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, String> {

}
