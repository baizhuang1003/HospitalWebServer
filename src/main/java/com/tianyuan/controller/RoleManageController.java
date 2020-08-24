package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.LimitBean;
import com.tianyuan.bean.RoleBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EasyTree;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.LimitRepository;
import com.tianyuan.repository.RoleRepository;

@RestController
public class RoleManageController extends BaseManageController {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	LimitRepository limitRepository;
	
	private final String moduleid = "4001";
	
	@GetMapping("role/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/role/index");
		return model;
	}
	
	@PostMapping("role/list")
	public EntityPager<RoleBean> list(int page,int rows){
		Initialize();
		if(page<1)page=1;
		if(rows<1)rows=10;
		EntityPager<RoleBean> list = roleRepository.pageSelect(page,rows, "orgcode = '"+this.getCode()+"'", "updatetime desc");
		return list;
	}
	
	@GetMapping("role/edit")
	public ModelAndView edit(String id) {
		Initialize();
		ModelAndView model = new ModelAndView();
		RoleBean entity = roleRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new RoleBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/role/edit");
		return model;
	}
	
	@PostMapping("role/edit")
	public AjaxResult edit(RoleBean entity) {
		Initialize();
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
		entity.setOrgcode(this.getCode());

        if (roleRepository.exits("id<>'"+entity.getId()+"' and  orgcode='"+this.getCode()+"' and name ='"+entity.getName()+"'")) return onFailed("角色名称已存在");
        try {
        	if (entity.getId()==null || entity.getId().equals("")) roleRepository.insertRow(entity);
        	else roleRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("role/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 roleRepository.deleteRow(" id='"+id+"'");
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
	
	@GetMapping("role/func")
	public ModelAndView func(String id) {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.addObject("roleid", id);
		model.setViewName(prefix+"/role/func");
		return model;
	}
	
	@PostMapping("role/savefunc")
	public AjaxResult saveFunc(String id,String funcs) {
		Initialize();
		RoleBean entity = roleRepository.selectRow(" id='"+id+"'");
        if (entity == null || entity.getId()==null || entity.getId().equals("")) return onFailed("参数错误");
        entity.setLimits(funcs);
        entity.setUpdateuid(this.getUserId());
        try {
        	roleRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@GetMapping("role/getdropdown")
	public List<EasyTree> GetDropdown()
    {
		Initialize();
        List<RoleBean> list = roleRepository.selectAll("orgcode='" + this.getCode() + "'", "");
        List<EasyTree> result = new ArrayList<EasyTree>();
        for (RoleBean item : list)
        {
        	EasyTree model = new EasyTree();
        	model.setId(item.getId());
        	model.setText(item.getName());
            result.add(model);
        }
        return result;
    }

	@GetMapping("role/gettree")
    public List<EasyTree> GetTree(String id)
    {
		Initialize();
        List<LimitBean> limitList = limitRepository.selectAll("", "id,sort");
        RoleBean role = roleRepository.selectRow(" id='"+id+"'");
        if(role.getLimits()==null) role.setLimits("");
        String[] funcs = role.getLimits().split(",");
        List<EasyTree> result = new ArrayList<EasyTree>();
        result.addAll(CreateChildTree(limitList, 0, funcs));
        return result;
    }
    private List<EasyTree> CreateChildTree(List<LimitBean> list, int parentid, String[] funcs)
    {
        List<LimitBean> temp = new ArrayList<LimitBean>();
        for (LimitBean item : list) {
			if(item.getParentid()==parentid) temp.add(item);
		}
        List<EasyTree> resultList = new ArrayList<EasyTree>();
        for (LimitBean item : temp){
        	EasyTree model = new EasyTree();
        	model.setId(item.getId()+"");
        	model.setText(item.getName());
        	model.setChildren(CreateChildTree(list, item.getId(), funcs));
            if (model.getChildren().size() < 1) {
            	boolean result = false;
            	for (String str : funcs) {
					if(str.equals(item.getId()+"")) result=true;
				}
            	model.setChecked(result);
            }
            resultList.add(model);
        }
        return resultList;
    }
}
