package com.example.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query("SELECT x FROM Article x WHERE x.calendarDate = :sqlArticleDate")
	Article findByCalendarDate(@Param("sqlArticleDate") Date sqlArticleDate);

	@Query("UPDATE Article x SET x.title = :title , x.url = :url WHERE x.calendarDate = :calendarDate")
	@Modifying
	int updateByCalendarDate(@Param("title") String title, @Param("url") String url,
			@Param("calendarDate") Date calendarDate);

	@Modifying
	@Query("DELETE FROM Article x WHERE x.calendarDate = :calendarDate")
	int deleteByCalendarDate(@Param("calendarDate") Date calendarDate);

	@Modifying
	@Query("DELETE FROM Article x WHERE x.calendarDate BETWEEN :calendarMonth and :lastDay")
	int deleteByCalendarMonth(@Param("calendarMonth") Date calendarMonth, @Param("lastDay") Date lastDay);
}