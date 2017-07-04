package com.example.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.repository.ThemeRepository;

@Service
@Transactional
public class AdventCalendarService {

	@Autowired
	ThemeRepository themeRepository;

	public List<CalendarDay> generateCalendarDays(Theme theme) {

		List<CalendarDay> cld = new ArrayList<CalendarDay>();

		Calendar calendar = Calendar.getInstance();

		//多分違う。最初に表示するのは当月のカレンダーのはず。
		Date gotMonth = theme.getCalendarmonth();
		
		//カレンダー内で比較できるように月、年を抽出
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		String stringMonth = mm.format(gotMonth);
		String stringYear = yyyy.format(gotMonth);
		
		//DBの情報からカレンダーを作ります
		int month = Integer.parseInt(stringMonth);
		int year= Integer.parseInt(stringYear);
		
		//有効な日付を取得
		int[] enableddates = theme.getEnableddates();
		
		//calendarはこれじゃダメな気がするので明日聞く。
		CalendarDay calendarDay = new CalendarDay(month, calendar);

		// 今月が何曜日から開始されているか確認する 
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// 先月が何日までだったかを確認する
		calendar.set(year, month, 0);
		int beforeMonthlastDay = calendar.get(Calendar.DATE);

		// 今月が何日までかを確認する
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);

		// 先月分の日付を格納する
		for (int i = startWeek - 2; i >= 0; i--) {
			
				cld.add(beforeMonthlastDay - i, null);
			}

		// 今月分の日付を格納する
		for (int i = 1; i <= thisMonthlastDay; i++) {
			
			if(Arrays.asList(enableddates).contains(i)){
			cld.add(i, calendarDay);
			}else{
				cld.add(i,null);
			}
			
		}

		// 翌月分の日付を格納する
		int nextMonthDay = 1;
		
			while(cld.size() % 7 != 0){	
				cld.add(nextMonthDay++, null);
			}
		
		return cld;
	}
}