package com.example.domain;

import java.util.Date;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

import lombok.Data;

public class CalendarDay {
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	ThemeRepository themeRepository;

	public boolean isAvalable() {
		if(){
		return true;
		}else{
			return false;
		}
	}

	public boolean isEnabled() {
		if(){
		return true;
		}else{
			return false;
		}
	}

	public boolean isRegistered() {
		if () {
			return true;
		} else {
			return false;
		}
	}

}
