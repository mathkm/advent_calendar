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

@Service
public class AdventCalendarService {

	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	ArticleRepository articleRepository;
	
	public List<CalendarDay> generateCalendarDays() {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
    	cal.set(year,month,1,0,0,0);
    	Date calendarMonth = cal.getTime();
    	
		List<CalendarDay> list = new ArrayList<CalendarDay>();
		
		CalendarDay calendarDay = new CalendarDay(calendarMonth,cal,themeRepository,articleRepository);
		
		int subDay = cal.get(Calendar.DAY_OF_WEEK) - 7;
        if (subDay < 0) {

            cal.add(Calendar.DAY_OF_MONTH, subDay);

        }

        boolean flag = true;
        //ここもたぶんおかしいです
        while(flag == true){
        	for(int i = 1 ; i == 7 ; i++){
        		
        		cal.add(Calendar.DAY_OF_MONTH, i);
        		list.add(calendarDay);
        		
        	}
        	
        	if(list.size() == 35){
        		
        		int lastday = cal.get(Calendar.DAY_OF_MONTH);
        		int checkmonth = cal.get(Calendar.MONTH) + 1;
        		int[] monthlist = {1,3,5,7,8,10,12};
        		int[] monthlist2 = {2,4,6,9,11};
        			
        			if (lastday == 29 && checkmonth != 2) {
        			
        				flag = true;
        				
        			} else if (lastday == 30 &&
        					Arrays.asList(monthlist).contains(checkmonth) ){
        				
        				flag = true;
        			
        			} else if (lastday == 29 && checkmonth == 2){
        				
        				flag = false;
        				
        			} else if (lastday == 30 &&
        					Arrays.asList(monthlist2).contains(checkmonth)){
        				
        				flag = false;
        				
        			} else if (lastday != 29 || lastday != 30 && lastday < 7) {
        				
        				flag = false;
        				
        			}
        		
        	}
        	
        	if(list.size() == 42){
        		
        		flag = false;
        		
        	}
        	
        }
        
		return list;
		
	}
	
}