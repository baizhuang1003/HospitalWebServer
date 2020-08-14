package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.Userbean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.Common;
import com.tianyuan.repository.UserRepository;




@RestController
public class LoginController extends BaseHospitalController {
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("login/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.addObject("title","德仁医院" );
		model.addObject("msg", "");
		model.addObject("username","");
		model.setViewName(prefix+"/login/index");
		return model;
	}
	
	@RequestMapping(value = "login/index", method = RequestMethod.POST)
	public AjaxResult loginHospital(String username,String password) {
		if (username == null || username.equals("")) {
			return onFailed("账号不能为空");
		}
		if(password==null||password.equals("")) {return onFailed("密码不能为空");
		}
		
		password = Common.md5Encode(password);
		System.out.println(password);
		Userbean entity = userRepository.selectRow("(USERNAME='"+username+"' or MOBILE='"+username+"') and PASSWORD='"+password+"'");
		if(entity==null || entity.getUserId()<1) return onFailed("账号密码错误"); 
		//addCookie(usercookie,entity.getUSER_ID());
		return onSuccess("登录成功");
	}
	
	@GetMapping("login/logout")
	public AjaxResult logout() {
		//removeCookie(usercookie);
		//removeCookie(codecookie);
		return onSuccess("退出成功");
	}
}
