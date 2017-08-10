package com.example.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Article;
import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.domain.User;
import com.example.domain.AdventCalendar;
import com.example.service.AdventCalendarService;
import com.example.service.ArticleService;
import com.example.service.Formatter;
import com.example.service.ThemeService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope("prototype")
@RequestMapping("/")

public class TopController {

	@Autowired
	AdventCalendarService adventCalendarService;

	@Autowired
	ThemeService themeService;

	@Autowired
	UserService userService;

	@Autowired
	ArticleService articleService;

	@Autowired
	AdventCalendar adventCalendar;

	@Autowired
	Formatter format;

	@ModelAttribute
	ArticleForm setUpForm() {
		return new ArticleForm();
	}

	@GetMapping
	String list(Model model) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.set(year, month, 1, 0, 0, 0);
		Date calendarMonth = cal.getTime();
		adventCalendar.setCalendarMonth(calendarMonth);
		List<CalendarDay[]> calendar = adventCalendarService.generateCalendarDays(calendarMonth);
		Theme theme = themeService.findByCalendarMonth();
		List<User> users = userService.findAll();
		boolean isNext = themeService.isNext();
		boolean isBack = themeService.isBack();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		int loginid;
		// ログイン情報がuserdetailsのサブクラスとして存在するときはUSERNAMEをログイン情報からゲットして返す。
		// そうでない場合はAnonymousを返す。
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 取得したusernameをもとに、テンプレートでログイン中のuserのidを取得できるロジック。
		User loginuser = userService.findByUsername(username);
		if (loginuser != null) {
			loginid = loginuser.getId();
		} else {
			loginid = 0;
		}
		model.addAttribute("loginid", loginid);
		model.addAttribute("username", username);
		model.addAttribute("calendar", calendar);
		model.addAttribute("theme", theme);
		model.addAttribute("users", users);
		model.addAttribute("isNext", isNext);
		model.addAttribute("isBack", isBack);
		return "/index";
	}

	@GetMapping(path = "nextmonth")
	String nextmonth(Model model) {
		java.util.Date nowCalendarMonth = adventCalendar.getCalendarMonth();
		if (nowCalendarMonth == null) {
			return "redirect:/";
		}
		java.util.Date calendarMonth = adventCalendarService.addMonth(nowCalendarMonth);
		adventCalendar.setCalendarMonth(calendarMonth);
		List<CalendarDay[]> calendar = adventCalendarService.generateCalendarDays(calendarMonth);
		Theme theme = themeService.findByCalendarMonth();
		List<User> users = userService.findAll();
		boolean isNext = themeService.isNext();
		boolean isBack = themeService.isBack();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		int loginid;
		// ログイン情報がuserdetailsのサブクラスとして存在するときはUSERNAMEをログイン情報からゲットして返す
		// そうでない場合は
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 取得したusernameをもとに、テンプレートでログイン中のuserのidを取得できるロジック。
		User loginuser = userService.findByUsername(username);
		if (loginuser != null) {
			loginid = loginuser.getId();
		} else {
			loginid = 0;
		}
		model.addAttribute("loginid", loginid);
		model.addAttribute("calendar", calendar);
		model.addAttribute("username", username);
		model.addAttribute("theme", theme);
		model.addAttribute("users", users);
		model.addAttribute("isNext", isNext);
		model.addAttribute("isBack", isBack);
		return "/index";
	}

	@GetMapping(path = "backmonth")
	String backmonth(Model model) {
		java.util.Date nowCalendarMonth = adventCalendar.getCalendarMonth();
		if (nowCalendarMonth == null) {
			return "redirect:/";
		}
		java.util.Date calendarMonth = adventCalendarService.minMonth(nowCalendarMonth);
		adventCalendar.setCalendarMonth(calendarMonth);
		List<CalendarDay[]> calendar = adventCalendarService.generateCalendarDays(calendarMonth);
		Theme theme = themeService.findByCalendarMonth();
		List<User> users = userService.findAll();
		boolean isNext = themeService.isNext();
		boolean isBack = themeService.isBack();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		int loginid;
		// ログイン情報がuserdetailsのサブクラスとして存在するときはUSERNAMEをログイン情報からゲットして返す
		// そうでない場合は
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 取得したusernameをもとに、テンプレートでログイン中のuserのidを取得できるロジック。
		User loginuser = userService.findByUsername(username);
		if (loginuser != null) {
			loginid = loginuser.getId();
		} else {
			loginid = 0;
		}
		model.addAttribute("loginid", loginid);
		model.addAttribute("username", username);
		model.addAttribute("calendar", calendar);
		model.addAttribute("theme", theme);
		model.addAttribute("users", users);
		model.addAttribute("isNext", isNext);
		model.addAttribute("isBack", isBack);
		return "/index";
	}

	@PostMapping(path = "create")
	String create(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return list(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleService.create(article);
		return "redirect:/";

	}

	@ResponseBody
	@PostMapping(value = "/createAjax", produces = "application/json; charset=UTF-8")
	String createAjax(@RequestBody String json) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObject = mapper.readTree(json);
		int userid = jsonObject.get(0).get("value").asInt();
		String strCalendarDate = jsonObject.get(1).get("value").asText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date javaDate = null;
		try {
			javaDate = sdf.parse(strCalendarDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date calendarDate = format.parseSql(javaDate);
		String title = jsonObject.get(2).get("value").asText();
		String url = jsonObject.get(3).get("value").asText();
		Article article = new Article();
		article.setCalendarDate(calendarDate);
		article.setUserid(userid);
		article.setTitle(title);
		article.setUrl(url);
		// BeanUtils.copyProperties(createData, article);
		articleService.create(article);
		String data = mapper.writeValueAsString(article);
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/editAjax", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	String editAjax(@RequestBody String json) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObject = mapper.readTree(json);
		String strCalendarDate = jsonObject.get(1).get("value").asText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date javaDate = null;
		try {
			javaDate = sdf.parse(strCalendarDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date calendarDate = format.parseSql(javaDate);
		String title = jsonObject.get(2).get("value").asText();
		String url = jsonObject.get(3).get("value").asText();
		// BeanUtils.copyProperties(createData, article);
		articleService.update(title, url, calendarDate);
		Article article = new Article();
		article.setCalendarDate(calendarDate);
		article.setUrl(url);
		article.setTitle(title);
		String data = mapper.writeValueAsString(article);
		return data;
	}

	@PostMapping(path = "edit")
	String edit(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return list(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		String title = article.getTitle();
		String url = article.getUrl();
		java.sql.Date calendarDate = article.getCalendarDate();
		articleService.update(title, url, calendarDate);
		return "redirect:/";
	}

	@PostMapping(path = "delete")
	String delete(@RequestParam java.sql.Date calendarDate) {
		articleService.delete(calendarDate);
		return "redirect:/";
	}

}
