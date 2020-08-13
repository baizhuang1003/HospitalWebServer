package com.tianyuan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@RestController
public class LoginController extends BaseHospitalController {

	@GetMapping("login/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.setViewName(prefix+"/login/index");
		return model;
	}
}
