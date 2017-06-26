package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme, String> {
	@Query("SELECT x FROM Theme x WHERE calendar_month =:calendar_month")
	Theme findOneByCalendarMonth(@Param("calendar_month") int calendarMonth);
}
