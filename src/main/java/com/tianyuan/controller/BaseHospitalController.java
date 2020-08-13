package com.tianyuan.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.CookieHelper;

@Controller
@RequestMapping("manage")
public class BaseHospitalController {

	@Value("${cookie.user.key}")
	private String cookie_user_key;
	@Value("${cookie.user.age}")
	private int cookie_user_age;
	@Value("${file.upload.liunx}")
	String linuxFilePath;
	@Value("${file.upload.windows}")
	String windowsFilePath;
	
	@Autowired
	HttpServletRequest req;
	@Autowired
	HttpServletResponse res;
	
	public static final String prefix="manage";
	
	protected int getSchoolId() {
		String value=getCookieValue();
		if(value.startsWith(prefix)) {
			String str = value.replace(prefix, "");
			String[] arrString = str.split("_");
			return Integer.parseInt(arrString[0]);
		}else {
			return 0;
		}
	}
	protected int getUserId() {
		String value=getCookieValue();
		if(value.startsWith(prefix)) {
			String str = value.replace(prefix, "");
			String[] arrString = str.split("_");
			return Integer.parseInt(arrString[1]);
		}else {
			return 0;
		}
	}
	
	protected File getRealFile(String path) {
		String filePath = "";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win"))
			// filePath = "d:/wisdom/";
			filePath = windowsFilePath;
		else if (os.toLowerCase().contains("mac"))
			filePath = System.getProperty("user.home") + "/wisdom/";
		else
			filePath = linuxFilePath;

		File file = new File(filePath, path);
		if (!os.toLowerCase().startsWith("win"))
			file.setWritable(true, false);

		if (!file.exists()) {
			File fileParent = file.getParentFile();
			if (!os.toLowerCase().startsWith("win"))
				fileParent.setWritable(true, false);
			fileParent.mkdirs();
		}
		return file;
	}
	
	protected void addCookie(String value) {
		CookieHelper.addCookie(res, cookie_user_key, cookie_user_age, value);
	}
	protected void addRemberCookie(String value) {
		CookieHelper.addCookie(res, "ck", cookie_user_age, value);
	}
	protected String getCookieValue() {
		return CookieHelper.getStringValue(req, cookie_user_key);
	}
	protected String getRemberCookieValue() {
		return CookieHelper.getStringValue(req, "ck");
	}
	protected void removeCookie() {
		CookieHelper.removeCookie(req, res, cookie_user_key);
	}
	protected void removeRemberCookie() {
		CookieHelper.removeCookie(req, res, "ck");
	}
	
	protected AjaxResult onFailed(String msg) {
		return new AjaxResult(400, null, msg);
	}
	
	protected AjaxResult onSuccess(String msg) {
		return new AjaxResult(200, null, msg);
	}
	
	protected AjaxResult onSuccess(Object content,String msg) {
		return new AjaxResult(200, content, msg);
	}
}
