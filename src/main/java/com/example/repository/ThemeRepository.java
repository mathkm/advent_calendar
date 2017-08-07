package com.example.repository;

import java.sql.Date;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.domain.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
	// @Query("SELECT x FROM Theme x ORDER BY x.id")
	// Theme findAllOrderById();
	@Query("SELECT x FROM Theme x WHERE x.calendarMonth = :calendarMonth")
	public Theme findByCalendarMonth(@Param("calendarMonth") java.sql.Date calendarMonth);

	@Query("SELECT x FROM Theme x WHERE x.calendarMonth > :calendarMonth ORDER BY x.calendarMonth ASC")
	public List<Theme> findByNextCalendarMonth(@Param("calendarMonth") java.sql.Date calendarMonth);

	@Query("SELECT x FROM Theme x WHERE x.calendarMonth < :calendarMonth ORDER BY x.calendarMonth DESC")
	public List<Theme> findByBackCalendarMonth(@Param("calendarMonth") java.sql.Date calendarMonth);

	@Query("SELECT x FROM Theme x ORDER BY x.calendarMonth DESC")
	public List<Theme> findAllOrderByCalendarMonth();

}
