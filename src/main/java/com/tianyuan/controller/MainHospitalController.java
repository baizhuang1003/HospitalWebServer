package com.tianyuan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class MainHospitalController {

	@GetMapping("main/index")
	public ModelAndView indexView() {
		ModelAndView model = new ModelAndView();
		model.addObject("name", "超级管理员");
		 model.setViewName("manage/main/index");
		return model;
	}
	
	@GetMapping("main/home")
	public ModelAndView homeView() {
		ModelAndView model = new ModelAndView();
		model.setViewName("manage/main/home");
		return model;
	}
}
