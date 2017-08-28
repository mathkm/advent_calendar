package com.example.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.domain.User;
import com.example.repository.ThemeRepository;
import com.example.service.AdventCalendarService;
import com.example.service.ArticleService;
import com.example.service.Formatter;
import com.example.service.ThemeService;
import com.example.service.UserService;
import com.example.web.ThemeForm;

@Transactional
@Controller
public class ThemeController {
	@Autowired
	ThemeService themeService;
	@Autowired
	UserService userService;
	@Autowired
	ArticleService articleService;
	@Autowired
	AdventCalendarService adventCalendar;
	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	Formatter formatter;

	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	SimpleDateFormat MM = new SimpleDateFormat("MM");
	SimpleDateFormat dd = new SimpleDateFormat("dd");
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	@ModelAttribute
	ThemeForm setUpForm() {
		return new ThemeForm();
	}

	@GetMapping(path = "/theme")
	String list(Model model) {

		List<Theme> theme = themeService.findAll();
		model.addAttribute("theme", theme);
		return "theme/list";

	}

	@GetMapping(path = "/theme/create")
	String createForm(ThemeForm form, Model model) {
		cal.set(year, month, 1, 0, 0, 0);
		Date calendarMonth = cal.getTime();
		java.sql.Date sqlCalendarMonth = formatter.parseSql(calendarMonth);
		Theme nowTheme = themeRepository.findByCalendarMonth(sqlCalendarMonth);
		List<Theme> next = themeRepository.findByNextCalendarMonth(sqlCalendarMonth);

		// 今月のテーマがない場合
		if (nowTheme == null) {
			// なにもしない（今月のカレンダーテーマを作る）
			// 今月のテーマがあって、次以降のテーマが全く無い場合
		} else if (nowTheme != null && next.isEmpty()) {
			cal.add(Calendar.MONTH, 1);
			// 今月のテーマがあって、次以降のテーマに一つでもある場合
		} else if (nowTheme != null && next.isEmpty() == false) {

			cal.add(Calendar.MONTH, 1);
			Date nextMonth = cal.getTime();
			String strNextMonth = ymd.format(nextMonth);

			// 次の月と、一つ先のテーマの月が同じだった場合
			Date oneHigher = next.get(0).getCalendarMonth();
			String strOneHigher = ymd.format(oneHigher);
			if (strNextMonth.equals(strOneHigher)) {
				// カレンダー月とテーマ月が同じな限りカレンダーのMonthを足していく
				// 8/3に解決する。
				// カレンダーの月が次のテーマの一つ上になるまで足して、そのときにまた次のテーマとチェックして、次のテーマと同じではなくなるまで足す。
				// treeset使いませんでした(SQLで今月より上という部分はクリアしてるから)
				int count = 0;
				int nextSize = next.size() - 1;
				while (strNextMonth.equals(strOneHigher)) {
					nextMonth = cal.getTime();
					strNextMonth = ymd.format(nextMonth);
					if (count <= nextSize) {
						oneHigher = next.get(count).getCalendarMonth();
					}
					strOneHigher = ymd.format(oneHigher);
					if (strNextMonth.equals(strOneHigher)) {
						cal.add(Calendar.MONTH, 1);
					}
					count++;
				}
			}
		}

		Date createThemeMonth = cal.getTime();
		java.sql.Date sqlCreateThemeMonth = formatter.parseSql(createThemeMonth);

		List<CalendarDay[]> calendar = adventCalendar.generateEditCalendar(sqlCreateThemeMonth);
		List<User> users = userService.findAll();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		// ログイン情報がuserdetailsのサブクラスとして存在するときはUSERNAMEをログイン情報からゲットして返す
		// そうでない場合は
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("nowMonth", createThemeMonth);
		model.addAttribute("calendar", calendar);
		model.addAttribute("users", users);
		model.addAttribute("username", username);
		return "/theme/create";
	}

	@PostMapping(path = "/theme/create")
	String create(@Validated ThemeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return createForm(form, model);
		}
		Theme theme = new Theme();
		BeanUtils.copyProperties(form, theme);
		themeService.create(theme);
		return "redirect:/theme";
	}

	@GetMapping(path = "/theme/edit", params = "form")
	String editForm(ThemeForm form, @RequestParam Integer id, Model model) {
		Theme theme = themeService.findOne(id);
		List<CalendarDay[]> calendar = adventCalendar.generateEditCalendar(theme.getCalendarMonth());
		List<User> users = userService.findAll();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		// ログイン情報がuserdetailsのサブクラスとして存在するときはUSERNAMEをログイン情報からゲットして返す
		// そうでない場合は
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("calendar", calendar);
		model.addAttribute("theme", theme);
		model.addAttribute("users", users);
		model.addAttribute("username", username);
		return "/theme/edit";
	}

	@PostMapping(path = "/theme/edit")
	String edit(@RequestParam Integer id, @Validated ThemeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return editForm(form, id, model);
		}
		String name = form.getName();
		String detail = form.getDetail();
		String enabledDates = form.getEnabledDates();
		int updatedUserid = form.getUpdatedUserid();
		themeService.update(name, detail, enabledDates, updatedUserid, id);
		return "redirect:/theme";
	}

	@PostMapping(path = "/theme/back")
	String goToTop() {
		return "redirect:/theme";
	}

	@PostMapping(path = "/theme/delete")
	String delete(@RequestParam Integer id, @RequestParam java.sql.Date calendarMonth) {
		themeService.delete(id);
		String strCldYear = yyyy.format(calendarMonth);
		String strCldMonth = MM.format(calendarMonth);
		int cldYear = Integer.parseInt(strCldYear);
		int cldMonth = Integer.parseInt(strCldMonth);
		cal.set(cldYear, cldMonth - 1, 1, 0, 0, 0);
		int maxDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(cldYear, cldMonth - 1, maxDay, 0, 0, 0);
		java.sql.Date lastDay = new java.sql.Date(cal.getTimeInMillis());
		articleService.deleteOneMonth(calendarMonth, lastDay);
		return "redirect:/theme";
	}

}
