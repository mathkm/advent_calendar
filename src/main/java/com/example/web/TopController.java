package com.example.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Article;
import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.service.AdventCalendarService;
import com.example.service.ThemeService;

@Controller
@RequestMapping("/")

public class TopController {

	@Autowired
	AdventCalendarService adventCalendar;
	
	@Autowired
	ThemeService themeService;
	
	@GetMapping
	String list(Model model) {
		//List<CalendarDay> calendar = adventCalendar.generateCalendarDays();
		//List<Theme> theme = themeService.findOne();
		//model.addAttribute("calendar", calendar);
		//model.addAttribute("theme",theme);
		return "/index";
	}

}
