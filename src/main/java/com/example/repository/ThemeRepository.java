package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme,Date> {
	@Query("SELECT x FROM Theme x ORDER by x.calendar_month")
	List<Theme> findAllOrderById();
	@Query("SELECT x FROM Theme x calendar_month = :calendar_month")
	List<Theme> findOneByCalendarMonth(@Param("calendar_month")int calendar_month);
}
