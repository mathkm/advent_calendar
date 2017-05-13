package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userpage")
public class UserpageController {
	@GetMapping
	String list(Model model){
		return "user/index";
	}
}
