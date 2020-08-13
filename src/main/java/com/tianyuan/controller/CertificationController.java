package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.OrganizationRepository;

/**
 * 认证管理
 * @author Administrator
 *
 */
@RestController
public class CertificationController extends BaseHospitalController {

	
	@Autowired
	OrganizationRepository organizationRepository;
	@GetMapping("certification/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		OrganizationBean entity = new OrganizationBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/certification/index");
		return model;
	}
	
	@PostMapping("certification/list")
	public EntityPager<OrganizationBean> list(int page,int rows){
		String where ="1=1";
		EntityPager<OrganizationBean> list = organizationRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	
	//审核
	@GetMapping("certification/cert")
	public ModelAndView cert(int id) {
		OrganizationBean entity = organizationRepository.selectRow(" id="+id);
		if(entity==null)entity =new OrganizationBean();
		ModelAndView model = new ModelAndView();
		model.addObject("obj", entity);
		model.setViewName(prefix+"/certification/cert");
		return model;
	}	
	
	@PostMapping("certification/cert")
	public AjaxResult cert(OrganizationBean model){
		OrganizationBean modeldeal = organizationRepository.selectRow("id="+model.getId());
		//User entity = userRepository.selectRow("id="+getUserId());
		modeldeal.setAudituser("超级管理员");
		modeldeal.setCertificationstatus(model.getCertificationstatus());
		boolean result =false;
		result = organizationRepository.updateRow(modeldeal);
		return result?onSuccess("审核成功"):onFailed("审核失败");
	}
	
	
}
