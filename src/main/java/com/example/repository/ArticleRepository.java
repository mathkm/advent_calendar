package com.example.repository;

import java.util.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query("SELECT x FROM Article x WHERE calendar_date = :calendar_date")
	Article findOneByCalendarDate(@Param("calendar_date") Calendar calendarDate);
}