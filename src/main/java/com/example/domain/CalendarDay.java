package com.example.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

public class CalendarDay {
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	ThemeRepository themeRepository;

	public boolean isAvalable() {
		if (true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isEnabled() {
		if (true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRegistered() {
		if (true) {
			return true;
		} else {
			return false;
		}
	}

}
