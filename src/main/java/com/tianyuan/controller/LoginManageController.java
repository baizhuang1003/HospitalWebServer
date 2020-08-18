package com.tianyuan.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.UserBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.Common;
import com.tianyuan.repository.UserRepository;

/**
 * 登录页面
 * @author Administrator
 *
 */
@RestController
public class LoginManageController extends BaseManageController  {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("login/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		//cmsstation station = cmsstationBuss.SelectRow(this.ServerDomain);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", "");
		map.put("orgcode", "");
		model.addObject("entity", map);
		model.setViewName(prefix+"/login/index");
		return model;
	}
	
	@PostMapping("login/dologin")
	public AjaxResult dologin(String username, String password) {
		UserBean entity = userRepository.selectRow(" username='"+username+"' and password='"+Common.md5Encode(password)+"'");
		
		if(entity==null || entity.getId().equals("")) return onFailed("账号密码错误"); 
		addCookie(usercookie,entity.getId());
		addCookie(codecookie,entity.getOrgcode());
		return onSuccess("登录成功");
	}
	
	@GetMapping("login/logout")
	public AjaxResult logout() {
		removeCookie(usercookie);
		removeCookie(codecookie);
		return onSuccess("退出成功");
	}
}
