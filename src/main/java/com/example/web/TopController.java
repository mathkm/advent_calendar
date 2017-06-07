package com.example.web;

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
import com.example.service.AdventCalendarService;
import com.example.service.ArticleService;

@Controller
@RequestMapping("/")
public class TopController {

	@Autowired
	AdventCalendarService adventCalendar;

	@Autowired
	ArticleService articleService;

	@GetMapping
	String list(Model model) {
		List<Integer> calendar = adventCalendar.CalendarDays();
		model.addAttribute("calendar", calendar);
		return "/index";
	}

	@ModelAttribute
	ArticleForm setUpForm() {
		return new ArticleForm();
	}

	@PostMapping(path = "create")
	String create(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return list(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleService.create(article);
		return "redirect:/index";
	}

	@PostMapping(path = "edit", params = "form")
	String editForm(@RequestParam Integer id, UserForm form) {
		Article article = articleService.findOne(id);
		BeanUtils.copyProperties(article, form);
		return "/index";
	}

	@PostMapping(path = "edit")
	String edit(@RequestParam Integer id, @Validated UserForm form, BindingResult result) {
		if (result.hasErrors()) {
			return editForm(id, form);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		article.setId(id);
		articleService.update(article);
		return "redirect:/index";
	}

	@GetMapping(path = "edit", params = "gotToTop")
	String goToTop() {
		return "redirect:/index";
	}

	@PostMapping(path = "delete")
	String delete(@RequestParam Integer id) {
		articleService.delete(id);
		return "redirect:/index";
	}

}
