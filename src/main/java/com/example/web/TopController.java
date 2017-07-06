package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.CalendarDay;
import com.example.service.AdventCalendarService;
import com.example.service.ThemeService;

@Controller
@RequestMapping("/")

public class TopController {
	
	@Autowired
	AdventCalendarService adventCalendarService;
	
	@GetMapping
	String list(Model model) {
		List <CalendarDay> calendar = adventCalendarService.generateCalendarDays();
		model.addAttribute("calendar",calendar);
		return "/index";
	}

}
