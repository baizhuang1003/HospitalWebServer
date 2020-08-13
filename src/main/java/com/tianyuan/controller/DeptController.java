package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.DeptBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.DeptRepository;

/**
 * 部门管理
 * @author Administrator
 *
 */
@RestController
public class DeptController extends BaseHospitalController {

	@Autowired
	DeptRepository deptRepository;
	
	
	@GetMapping("dept/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		DeptBean entity = new DeptBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dept/index");
		return model;
	}
	
	@PostMapping("dept/list")
	public EntityPager<DeptBean> list(int page,int rows,int  pid){
		String where = "1=1";
		if(pid >0 ) where +=" and pid ="+pid;
		EntityPager<DeptBean> list = deptRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("dept/edit")
	public ModelAndView edit(String id) {
		DeptBean entity = deptRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new DeptBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dept/edit");
		return model;
	}
	
	@PostMapping("dept/edit")
	public AjaxResult edit(DeptBean entity) {
        if (deptRepository.exits("id<>"+entity.getId()+" and deptname = "+entity.getDeptname()+" and pid= "+entity.getParentid())) return onFailed("部门已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) deptRepository.insertRow(entity);
        	else deptRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("dept/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 deptRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
