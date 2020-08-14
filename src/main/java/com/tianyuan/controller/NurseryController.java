package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.NurseryBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.NurseryRepository;

/**
 * 托养服务
 * @author Administrator
 *
 */
@RestController
public class NurseryController extends BaseHospitalController {

	@Autowired
	NurseryRepository nurseryRepository;
	
	@GetMapping("nursery/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		NurseryBean entity = new NurseryBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/nursery/index");
		return model;
	}
	
	@PostMapping("nursery/list")
	public EntityPager<NurseryBean> list(int page,int rows){
		String where = "";
		EntityPager<NurseryBean> list = nurseryRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("nursery/edit")
	public ModelAndView edit(int id) {
		NurseryBean entity = nurseryRepository.selectRow(" id="+id);
		if(entity==null) entity = new NurseryBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/nursery/edit");
		return model;
	}
	
	@PostMapping("nursery/edit")
	public AjaxResult edit(NurseryBean entity) {
        if (nurseryRepository.exits("id<>"+entity.getId()+" and name='"+entity.getName()+"' ")) return onFailed("托养方式已存在");
        try {
        	if (entity.getId()<1) nurseryRepository.insertRow(entity);
        	else nurseryRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("nursery/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 nurseryRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
