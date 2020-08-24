package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.OrganizationRepository;

@RestController
public class OrganizationController extends BaseManageController {

	@Autowired
	OrganizationRepository organizationRepository;
	
	
	private final String moduleid = "2001";
	
	@GetMapping("organization/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/organization/index");
		return model;
	}
	
	@PostMapping("organization/list")
	public EntityPager<OrganizationBean> list(int page,int rows,String pid){
		Initialize();
        EntityPager<OrganizationBean> list = organizationRepository.pageSelect(page, rows, "", "id");
        return list;
	}
	
	@GetMapping("organization/edit")
	public ModelAndView edit(int id) {
		Initialize();
		OrganizationBean entity = organizationRepository.selectRow(" id="+id);
		if(entity==null) entity = new OrganizationBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/organization/edit");
		return model;
	}
	
	@PostMapping("organization/edit")
	public AjaxResult edit(OrganizationBean entity) {
		Initialize();
        if (organizationRepository.exits("id<>"+entity.getId()+" and name='"+entity.getName()+"'")) return onFailed("机构已存在");
        try {
        	if (entity.getId().equals("")) organizationRepository.insertRow(entity);
        	else organizationRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("organization/delete")
	public AjaxResult delete(String id) {
		if (id==null || id.equals("")) return onFailed("参数错误");
		try {
			organizationRepository.deleteRow(" id='"+id+"'");
			return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
	
	
	
}
