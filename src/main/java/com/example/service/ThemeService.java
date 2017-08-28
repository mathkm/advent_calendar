package com.example.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.repository.ThemeRepository;

@Service
@Transactional
public class ThemeService {
	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	AdventCalendarService adventCalendar;

	public Theme findByCalendarMonth() {
		java.sql.Date calendarMonth = adventCalendar.getCalendarMonth();
		return themeRepository.findByCalendarMonth(calendarMonth);
	}

	public List<Theme> findAll() {
		return themeRepository.findAllOrderByCalendarMonth();
	}

	public Theme findOne(Integer id) {
		return themeRepository.findOne(id);
	}

	public Theme create(Theme theme) {
		return themeRepository.save(theme);
	}

	public int update(String name, String detail, String enabledDates, int updatedUserid, int id) {
		return themeRepository.updateById(name, detail, enabledDates, updatedUserid, id);
	}

	public void delete(Integer id) {
		themeRepository.delete(id);
	}

	// トップページで次の月に記事があるかどうかでカレンダーの動きを変えるためのろじっく
	public boolean isNext() {
		java.sql.Date calendarMonth = adventCalendar.getCalendarMonth();
		List<Theme> next = themeRepository.findByNextCalendarMonth(calendarMonth);
		if (next.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// 上の戻り版
	public boolean isBack() {
		java.sql.Date calendarMonth = adventCalendar.getCalendarMonth();
		List<Theme> back = themeRepository.findByBackCalendarMonth(calendarMonth);
		if (back.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}