package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ToppageController {
	@GetMapping
	String list(Model model){
		return "index";
	}
}
