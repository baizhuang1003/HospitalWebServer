package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.bean.ServiceItemsBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.ServiceItemsRepository;

/**
 * 服务项目管理
 * @author Administrator
 *
 */
@RestController
public class ServiceItemsController extends BaseHospitalController {

	@Autowired
	ServiceItemsRepository serviceItemsRepository;
	
	@GetMapping("serviceitem/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		ServiceItemsBean entity = new ServiceItemsBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/serviceitem/index");
		return model;
	}
	
	@PostMapping("serviceitem/list")
	public EntityPager<ServiceItemsBean> list(int page,int rows,String status ,String key){
		String where = "";
		EntityPager<ServiceItemsBean> list = serviceItemsRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("serviceitem/edit")
	public ModelAndView edit(String id) {
		ServiceItemsBean entity = serviceItemsRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new ServiceItemsBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/serviceitem/edit");
		return model;
	}
	
	@PostMapping("serviceitem/edit")
	public AjaxResult edit(ServiceItemsBean entity) {
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
        if (serviceItemsRepository.exits("id<>"+entity.getId()+" and name='"+entity.getName()+"' ")) return onFailed("服务项目已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) serviceItemsRepository.insertRow(entity);
        	else serviceItemsRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("serviceitem/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 serviceItemsRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
