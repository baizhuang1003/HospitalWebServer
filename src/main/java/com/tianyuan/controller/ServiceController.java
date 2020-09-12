package com.tianyuan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.ServiceBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.ServiceRepository;

@RestController
public class ServiceController extends BaseManageController {

	private final String moduleid = "700101";
	@Autowired
	ServiceRepository serviceRepository;
	
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
	
	@PostMapping("service/list")
	public EntityPager<Map<String, Object>> list(int page,int rows){
		Initialize();
        String where ="orgcode='"+getCode()+"'";
        
        return serviceRepository.pageSelectAll(page, rows, where, "updatetime desc");
	}
	
	@GetMapping("service/edit")
	public ModelAndView edit(String id) {
		Initialize();
		ServiceBean entity = serviceRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new ServiceBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/service/edit");
		return model;
	}
	
	@PostMapping("service/edit")
	public AjaxResult edit(ServiceBean entity) {
		Initialize();
        if (serviceRepository.exits("id<>"+entity.getId()+"and orgcode='"+entity.getOrgcode()+"' and name1='"+entity.getName1()+"'")) return onFailed("服务项目已存在");
        entity.setCreateuid(getUserId());
        entity.setUpdateuid(getUserId());
        entity.setOrgcode(getCode());
        try {
        	if (entity.getId()<1) serviceRepository.insertRow(entity);
        	else serviceRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("service/delete")
	public AjaxResult delete(String id) {
		if (id==null || id.equals("")) return onFailed("参数错误");
		try {
			serviceRepository.deleteRow(" id='"+id+"'");
			return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
