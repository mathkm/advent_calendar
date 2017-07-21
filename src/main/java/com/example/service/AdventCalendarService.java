package com.example.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.domain.Article;
import com.example.domain.CalendarDay;
import com.example.domain.Theme;
import com.example.repository.ArticleRepository;
import com.example.repository.ThemeRepository;

@Scope("prototype")
@Service
public class AdventCalendarService {

	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	ArticleRepository articleRepository;
	
	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	
	public List<CalendarDay> generateCalendarDays() {
		
		cal.set(year,month,1,0,0,0);
		Date calendarMonth = cal.getTime();
		
		List<CalendarDay> list = new ArrayList<CalendarDay>();
		
	   	// カレンダーの一番左上までカレンダーを引き戻す
	   	int firstweek = cal.get(Calendar.DAY_OF_WEEK);
	   	int subDay = 0;
	   	if(firstweek == 1){
	   	}else if(firstweek >= 2){
	   		
	   		while(firstweek > 1){
	   			subDay--;
	   			firstweek--;
	   		}
	   		
	   	}
	   	
	   	cal.add(Calendar.DAY_OF_MONTH, subDay);

	   	//whileコントロール用のフラグ
        boolean flag = true;
        
        while(flag){
        	//一週間ごとにコンストラクタを呼び起こして、Listに足していく
        	for(int i = 0 ; i <= 6 ; i++){
        		
        		if(list.size() == 0){
        		
        			CalendarDay calendarDay = new CalendarDay(calendarMonth,cal,themeRepository,articleRepository);
        			list.add(calendarDay);
        			
        			
        		}else{
        			
        			cal.add(Calendar.DAY_OF_MONTH, 1);
        			CalendarDay calendarDay = new CalendarDay(calendarMonth,cal,themeRepository,articleRepository);
        			list.add(calendarDay);
        		
        		}
        		
        	}
        	
        	//35番目の部分になったら終了させていい可能性が出るので、チェックを行う。
        	if(list.size() == 35){
        		
        		//35番目の日付
        		int lastday = cal.get(Calendar.DAY_OF_MONTH);
        		//その時の月
        		int checkmonth = cal.get(Calendar.MONTH) + 1;
        		//31日がある月
        		int[] intmonthlist = {1,3,5,7,8,10,12};
        		//30日までの月
        		int[] intmonthlist2 = {2,4,6,9,11};
        		
        		// 値の中を調べるためにIntegerのListに変換する
        		List<Integer> monthlist = new ArrayList<Integer>(intmonthlist.length);
        		for (int j = 0; j < intmonthlist.length; j++) {
        		    monthlist.add(intmonthlist[j]);
        		}
        		
        		List<Integer> monthlist2 = new ArrayList<Integer>(intmonthlist2.length);
        		for (int k = 0; k < intmonthlist2.length; k++) {
        		    monthlist2.add(intmonthlist2[k]);
        		}
        			//lastdayが29日で2月でなければtrueのまま。
        			if (lastday == 29 && checkmonth != 2) {
        			
        				flag = true;
        			//lastdayが30日で、31日がある月であればtrueのまま。	
        			} else if (lastday == 30 &&
        					monthlist.contains(checkmonth) ){
        				
        				flag = true;
        			//lastdayが29日で2月であればfalse。
        			} else if (lastday == 29 && checkmonth == 2){
        				
        				flag = false;
        				
        			//lastdayが30日で、31日がある月でなければfalse。
        			} else if (lastday == 30 &&
        					monthlist2.contains(checkmonth)){
        				
        				flag = false;
        				
        			//lastdayが少ない数字（いちお7未満とかで）か、31日であればfalse。
        			} else if (lastday != 29 || lastday != 30 && lastday < 7 || lastday == 31) {
        				
        				flag = false;
        				
        			}
        		
        	}
        	
        	if(list.size() == 42){
        		
        		flag = false;
        		
        	}
        	
        }
        
		return list;
		
	}
	
	//themeとの連携用
	public java.sql.Date getCalendarMonth(){
		cal.set(year,month,1,0,0,0);
		Date calendarMonth = cal.getTime();
		cal.setTime(calendarMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlCalendarMonth = new java.sql.Date(cal.getTimeInMillis());
		return sqlCalendarMonth;
	}
	
	// 次の月に行く処理を書く
	public void addMonth(){
	}

	// 前の月に行く処理を書く
	public void minMonth(){
	}
}