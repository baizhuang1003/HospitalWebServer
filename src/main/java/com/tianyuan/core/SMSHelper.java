package com.tianyuan.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class SMSHelper {
	
	private int effective;
	
	//短信存留时间
	public int getEffective() {
		this.effective = 5;
		return effective;
	}
	
	
	private static final String smsurlPath="http://api.sms.cn/sms/";
	private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
//	public boolean Send(String mobile,String code){
//		
//	   if(mobile==null||mobile.equals(""))return false;
//	   if(code==null||code.equals(""))return false;
//	   if(username==null||username.equals(""))return false;
//	   if(password==null||password.equals(""))return false;
//	   if(template==null||template.equals(""))return false;
//		
//		
//		Map<String, String> map = new HashMap<>();
//		map.put("ac", "send");
//		map.put("uid", username);
//		map.put("pwd", password);
//		map.put("mobile", mobile);
//		map.put("template", template);
//		map.put("content", "{\"code\":\""+code+"\"}");
//		map.put("encode", "utf8");
//		
//		Connection con = Jsoup.connect(smsurlPath);
//		con.header("User-Agent", userAgent);
//		con.header("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
//		Response rs;
//		try {
//			rs = con.ignoreContentType(true).method(Method.POST).data(map).execute();
//			if(rs==null)return false;
//			if(rs.body()==null)return false;
//			
//			System.out.println("发送短信验证码结果："+rs.body());
//			
//			JsonObject json = JsonHelper.getGson().fromJson(rs.body(), JsonObject.class);
//			String stat = json.get("stat").getAsString();
//			if(stat==null)return false;
//			if(stat.equals("100"))return true;
//			return false;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		
//		
//	}
	
	public static boolean Send(String username,String password,String moduleid,String mobile,String code){
		
		   if(mobile==null||mobile.equals(""))return false;
		   if(code==null||code.equals(""))return false;
		   if(username==null||username.equals(""))return false;
		   if(password==null||password.equals(""))return false;
		   if(moduleid==null||moduleid.equals(""))return false;
			
			
			Map<String, String> map = new HashMap<>();
			map.put("ac", "send");
			map.put("uid", username);
			map.put("pwd", password);
			map.put("mobile", mobile);
			map.put("template", moduleid);
			map.put("content", "{\"code\":\""+code+"\"}");
			map.put("encode", "utf8");
			
			Connection con = Jsoup.connect(smsurlPath);
			con.header("User-Agent", userAgent);
			con.header("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
			Response rs;
			try {
				rs = con.ignoreContentType(true).method(Method.POST).data(map).execute();
				if(rs==null)return false;
				if(rs.body()==null)return false;
				
				System.out.println("发送短信验证码结果："+rs.body());
				
				JsonObject json = JsonHelper.getGson().fromJson(rs.body(), JsonObject.class);
				String stat = json.get("stat").getAsString();
				if(stat==null)return false;
				if(stat.equals("100"))return true;
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
		}

}
