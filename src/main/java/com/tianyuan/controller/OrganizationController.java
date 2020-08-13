package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityDrop;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.OrganizationRepository;

/**
   *   机构设置
 * @author BZ 2020年8月6日17:01:50
 *
 */
@RestController
public class OrganizationController extends BaseHospitalController {

	@Autowired
	OrganizationRepository organizationRepository;
	
	@GetMapping("organization/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		OrganizationBean entity = new OrganizationBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/organization/index");
		return model;
	}
	
	@PostMapping("organization/list")
	public EntityPager<OrganizationBean> list(int page,int rows,int status ,String key){
		String where = "1=1";
		if(status>0) where =" status = "+status;
		if(!com.tianyuan.core.StringHelper.isnull(key)) where +=" and  organization ="+key;
		System.out.println(where);
		EntityPager<OrganizationBean> list = organizationRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("organization/edit")
	public ModelAndView edit(int id) {
		OrganizationBean entity = organizationRepository.selectRow(" id="+id);
		if(entity==null) entity = new OrganizationBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/organization/edit");
		return model;
	}
	
	@PostMapping("organization/edit")
	public AjaxResult edit(OrganizationBean entity) {
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
        if (organizationRepository.exits("id<>"+entity.getId()+" and region='"+entity.getRegion()+"' and organization='"+entity.getOrganization()+"'")) return onFailed("机构已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) organizationRepository.insertRow(entity);
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
			 organizationRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
		
	
	//认证状态
	@GetMapping("organization/state")
	public List<EntityDrop> userstate(){
		List<EntityDrop> list = new ArrayList<EntityDrop>();
		list.add(new EntityDrop("0", " "));
		list.add(new EntityDrop("1", "已审核"));
		list.add(new EntityDrop("2", "未通过"));
		list.add(new EntityDrop("3","待审核"));
		return list;
	}	
	
	
}
