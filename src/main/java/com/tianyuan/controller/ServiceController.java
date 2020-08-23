package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.model.UserOverload;

@RestController
public class ServiceController extends BaseManageController {

private final String moduleid = "100101";
	
	@GetMapping("service/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/service/index");
		return model;
	}
	
	@GetMapping("service/edit")
	public ModelAndView edit(String id) {
		Initialize();
		//DeptBean entity = deptRepository.selectRow(" id='"+id+"'");
		//if(entity==null) entity = new DeptBean();
		ModelAndView model = new ModelAndView();
		//model.addObject("entity", entity);
		model.setViewName(prefix+"/service/edit");
		return model;
	}
}
