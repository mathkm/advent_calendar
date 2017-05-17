package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.service.AdventCalendarService;

@Controller
@RequestMapping("/")
public class TopController {
	
	@Autowired
	AdventCalendarService adventCalendar;
	
	@GetMapping
	String list(Model model){
		List<Integer> calendar = adventCalendar.CalendarDays();
		model.addAttribute("calendar",calendar);
		return "/index";
	}
}
