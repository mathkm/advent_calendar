package com.example.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.service.AdventCalendarService;
import com.example.service.ThemeService;

@Controller
@Scope("prototype")
@RequestMapping("/")

public class TopController {
	
	@Autowired
	AdventCalendarService adventCalendarService;
	
	@Autowired
	ThemeService themeService;
	
	@GetMapping
	String list(Model model) {
		List <CalendarDay> calendar = adventCalendarService.generateCalendarDays();
		Theme theme = themeService.findByCalendarMonth();
		model.addAttribute("calendar",calendar);
		model.addAttribute("theme",theme);
		return "/index";
	}

}
