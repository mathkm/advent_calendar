package com.example.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query("SELECT x FROM Article x WHERE x.calendarDate = :sqlArticleDate")
	Article findByCalendarDate(@Param("sqlArticleDate") Date sqlArticleDate);
}