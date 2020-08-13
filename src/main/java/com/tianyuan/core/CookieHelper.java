package com.tianyuan.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

	
	public static String getStringValue(HttpServletRequest req,String key) {
		String result ="";
		Cookie[] cookies = req.getCookies();
		if(cookies==null||cookies.length<1)return "";
		for (Cookie item : cookies) {
			if (item.getName().toUpperCase().equals(key.toUpperCase())) {
				result = item.getValue();
				break;
			}
		}
		return result;
	}
	
	public static int getIntValue(HttpServletRequest req,String key,int defaultValue) {
		String result =getStringValue(req, key);
		if(result==null||result.equals(""))return defaultValue;
		Integer i = Integer.parseInt(result);
		return i==null?defaultValue:i.intValue();
	}
	
	
	public static void addCookie(HttpServletResponse res,String key,int age,String value) {
		if (value==null||value.equals("")) return;
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(age);
		cookie.setPath("/");
		res.addCookie(cookie);
	}
	
	public static void removeCookie(HttpServletRequest req,HttpServletResponse res,String key) {
		Cookie[] cookies =req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					res.addCookie(cookie);
				}
			}
		}
	}
	
}
