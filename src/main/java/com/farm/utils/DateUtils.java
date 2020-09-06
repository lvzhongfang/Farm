package com.farm.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.druid.util.StringUtils;

public class DateUtils {

	public static String date2String(String pattern,LocalDateTime date) throws Exception{
		String s = "";
		if(StringUtils.isEmpty(pattern))
			pattern = "yyyy-MM-dd HH:mm";
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			s = date.format(formatter);
		}catch(Exception e) {
			throw e;
		}
		return s;
	}
	
	public LocalDateTime str2Date(String pattern,String str) {
		
		LocalDateTime ldt = null;
		
		if(StringUtils.isEmpty(pattern))
			pattern = "yyyy-MM-dd HH:mm";
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			ldt = LocalDateTime.parse(str,formatter);
		}catch(Exception e) {
			throw e;
		}
		
		return ldt;
	}
}
