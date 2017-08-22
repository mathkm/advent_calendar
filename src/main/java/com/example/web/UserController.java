package com.example.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.domain.User;
import com.example.service.UserService;

@Controller
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;

	@ModelAttribute
	UserForm setUpForm() {
		return new UserForm();
	}

	@GetMapping
	String list(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "users/list";
	}

	@GetMapping(path = "/create")
	String createForm() {
		return "users/create";
	}

	@PostMapping(path = "/create")
	String create(@Validated UserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return list(model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userService.create(user);
		return "redirect:/users";
	}

	@GetMapping(path = "/edit", params = "form")
	String editForm(@RequestParam Integer id, UserForm form, Model model) {
		User user = userService.findOne(id);
		java.util.Date created = user.getCreated();
		java.util.Date updated = user.getUpdated();
		model.addAttribute("created", created);
		model.addAttribute("updated", updated);
		BeanUtils.copyProperties(user, form);
		return "users/edit";
	}

	@PostMapping(path = "/edit")
	String edit(@RequestParam Integer id, @Validated UserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return editForm(id, form, model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setId(id);
		userService.update(user);
		return "redirect:/users";
	}

	@PostMapping(path = "/edit", params = "goToTop")
	String goToTop() {
		return "redirect:/users";
	}

	@PostMapping(path = "/delete")
	String delete(@RequestParam Integer id) {
		userService.delete(id);
		return "redirect:/users";
	}
}
