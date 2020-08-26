package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.bean.UserBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.Common;
import com.tianyuan.core.Convertor;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.OrganizationRepository;
import com.tianyuan.repository.UserRepository;

@RestController
public class OrganizationController extends BaseManageController {

	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	UserRepository userRepository;
	
	
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
	
	@PostMapping("organ/list")
	public EntityPager<OrganizationBean> list(int page,int rows){
		Initialize();
		if(page<1)page=1;
		if(rows<1)rows=10;
		String where = getWhere();
		String order = " code asc";
		EntityPager<OrganizationBean> list = organizationRepository.pageSelect(page,rows,where,order);
		return list;
	}
	
	private String getWhere() {
		Initialize();
		String prov = this.getCode().substring(0, 2);
		String city = this.getCode().substring(2, 4);
		String zone = this.getCode().substring(4, 6);
		String where = "";
        if (this.getOrganLevel() == 0)//国家级
            where = "left(code,2) <> '00' and substring(code,3,2) = '00' and substring(code,5,2) = '00'";
        if (this.getOrganLevel() == 1)//省级
            where = "left(code,2)='" + prov + "' and substring(code,3,2) <> '00' and substring(code,5,2) = '00'";
        if (this.getOrganLevel() == 2)//市级
            where = "left(code,2)='" + prov + "' and substring(code,3,2) = '" + city + "' and substring(code,5,2) <> '00'";
        if (this.getOrganLevel() == 3)//区级
            where = "left(code,2)='" + prov + "' and substring(code,3,2) = '" + city + "' and substring(code,5,2) = '" + zone + "'";
        return where;
	}
	
	//添加修改
	@GetMapping("organ/edit")
	public ModelAndView edit(String id) {
		Initialize();
		ModelAndView model = new ModelAndView();
		OrganizationBean entity = organizationRepository.selectRow(" id='"+id+"'");
		String provinceid="",cityid="",zoneid="";
		if(entity==null) {
			entity = new OrganizationBean();
			entity.setRegionid("");
		}
		String region = Convertor.padLeft(entity.getRegionid(),6, '0');
		provinceid = Convertor.padLeft(region.substring(0, 2),6, '0');
		cityid = Convertor.padLeft(region.substring(0, 4),6, '0');
		zoneid = Convertor.padLeft(region.substring(0, 6),6, '0');
		model.addObject("entity", entity);
		model.addObject("OrganCode", getCode());
		model.addObject("provinceid", provinceid);
		model.addObject("cityid", cityid);
		model.addObject("zoneid", zoneid);
		model.setViewName(prefix+"/organization/edit");
		return model;
	}
	
	@PostMapping("organ/edit")
	public AjaxResult edit(OrganizationBean entity) {
		Initialize();
		if (entity.getId() == null) entity.setId("");
		entity.setCreateuid(this.getUserId());
        entity.setUpdateuid(this.getUserId());
        if (organizationRepository.exits("id <> '" + entity.getId() + "' and code ='" + entity.getCode() + "'")) return onFailed("组织机构已存在");
        try {
        	if (entity.getId()==null || entity.getId().equals("")) organizationRepository.insertRow(entity);
        	else organizationRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	//管理员账号
	@GetMapping("organ/editmanager")
	public ModelAndView editManager(String pid) {
		Initialize();
		ModelAndView model = new ModelAndView();
		OrganizationBean organ = organizationRepository.selectRow(" id='"+pid+"'");
		UserBean entity = userRepository.selectRow(" orgcode='" + organ.getCode() + "' and ismanager = 1 limit 1 ");
		if(entity==null) entity = new UserBean();
        if (entity == null || entity.getId()==null || entity.getId().equals("")) {
        	entity.setOrgcode(organ.getCode());
        	entity.setName("超级管理员");
        }
        entity.setPassword("");
        model.addObject("entity", entity);
		model.setViewName(prefix+"/organization/editmanager");
		return model;
	}
	
	@PostMapping("organ/editmanager")
	public AjaxResult editManager(UserBean model) {
		Initialize();
		if (model.getId()==null) model.setId("");
        if (userRepository.exits("id <> '"+model.getId()+"' and username = '"+model.getUsername()+"'")) return onFailed("账号已存在");
        UserBean entity = userRepository.selectRow("id ='"+model.getId()+"'");
        try {
        	if (entity == null || entity.getId()==null || entity.getId().equals("")) {
                entity = new UserBean();
                entity.setCreateuid(this.getUserId());
                entity.setDeptid(0);
                entity.setEmail("");
                entity.setIsmanager(1);
                entity.setMobile("");
                entity.setName("超级管理员");
                entity.setOrgcode(model.getOrgcode());
                entity.setPassword(model.getPassword());
                entity.setRoleid("");
                entity.setSex(0);
                entity.setUpdateuid(this.getUserId());
                entity.setUsername(model.getUsername());
                userRepository.insertRow(entity);
            }
            else
            {
            	entity.setUsername(model.getUsername());
            	entity.setPassword(Common.md5Encode(model.getPassword()));
            	entity.setUpdateuid(this.getUserId());
            	userRepository.updateRow(entity);
            }
            return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("organ/delete")
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
