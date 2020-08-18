package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.DeptBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EasyTree;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.DeptRepository;

@RestController
public class DeptManageController extends BaseManageController {

	@Autowired
	DeptRepository deptRepository;
	private final String moduleid = "100101";
	
	@GetMapping("dept/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dept/index");
		return model;
	}
	
	@GetMapping("dept/gettree")
	public List<EasyTree> getTree(String self){
		Initialize();
		if(self==null) self = "";
		String where="orgcode='" + this.getCode() + "'";
        if (!self.equals("")) where += " and id <> '"+self+"'";
        List<DeptBean> deptList = deptRepository.selectAll(where, "sort");
        List<EasyTree> resultList = new ArrayList<EasyTree>();
        EasyTree model = new EasyTree();
        model.setId("");
        model.setText("全部部门");
        model.setChildren(createChildTree(deptList, model));
        model.setIconCls("icon-menu");
        resultList.add(model);
        return resultList;
	}
	
	private List<EasyTree> createChildTree(List<DeptBean> list, EasyTree et){
		Initialize();
		List<EasyTree> resultList = new ArrayList<EasyTree>();
        List<DeptBean> temp = new ArrayList<DeptBean>();
        for (DeptBean item : list) {
			if(item.getParentid()==null) item.setParentid("");
			if(item.getParentid().equals(et.getId())) temp.add(item);
		}
        for (DeptBean item : temp) {
            if (item.getParentid() == null) item.setParentid("");
            EasyTree model = new EasyTree();
            model.setAttributes("");
            model.setId(item.getId());
            model.setText(item.getName());
            model.setChildren(createChildTree(list,model));
            if(item.getParentid()=="") model.setIconCls("icon-layout");
            else  model.setIconCls("icon-gears");
            resultList.add(model);
        }
        return resultList;
	}
	
	@PostMapping("dept/list")
	public EntityPager<DeptBean> list(int page,int rows,String pid){
		Initialize();
		String where = "orgcode='"+this.getCode()+"'";
        where += " and ifnull(parentid,'') = '" + pid + "'";
        EntityPager<DeptBean> list = deptRepository.pageSelect(page, rows, where, "sort");
        return list;
	}
	
	@GetMapping("dept/edit")
	public ModelAndView edit(String id) {
		Initialize();
		DeptBean entity = deptRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new DeptBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dept/edit");
		return model;
	}
	
	@PostMapping("dept/edit")
	public AjaxResult edit(DeptBean entity) {
		Initialize();
		if(entity.getId()==null) entity.setId("");
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
		entity.setOrgcode(this.getCode());
        if (deptRepository.exits("id<>'"+entity.getId()+"' and orgcode='"+entity.getOrgcode()+"' and name='"+entity.getName()+"'")) return onFailed("部门名称已存在");
        try {
        	if (entity.getId().equals("")) deptRepository.insertRow(entity);
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
			deptRepository.deleteRow(" id='"+id+"'");
			return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
