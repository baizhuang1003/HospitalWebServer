package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.ParameterBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.ParameterRepository;

@RestController
public class ParameterManageController extends BaseManageController {

	@Autowired
	ParameterRepository parameterRepository;
	
	private final String moduleid = "100102";
	
	@GetMapping("parameter/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/parameter/index");
		return model;
	}
	
	@PostMapping("parameter/list")
	public EntityPager<ParameterBean> list(int page,int rows,String pid){
		Initialize();
        EntityPager<ParameterBean> list = parameterRepository.pageSelect(page, rows, "", "id");
        return list;
	}
	
	@GetMapping("parameter/edit")
	public ModelAndView edit(int id) {
		Initialize();
		ParameterBean entity = parameterRepository.selectRow(" id="+id);
		if(entity==null) entity = new ParameterBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/nursery/edit");
		return model;
	}
	
	@PostMapping("parameter/edit")
	public AjaxResult edit(ParameterBean entity) {
		Initialize();
        if (parameterRepository.exits("id<>"+entity.getId()+" and name='"+entity.getName()+"'")) return onFailed("托养方式已存在");
        try {
        	if (entity.getId()<1) parameterRepository.insertRow(entity);
        	else parameterRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("parameter/delete")
	public AjaxResult delete(String id) {
		if (id==null || id.equals("")) return onFailed("参数错误");
		try {
			parameterRepository.deleteRow(" id='"+id+"'");
			return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
