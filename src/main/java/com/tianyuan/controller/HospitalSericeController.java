package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.HospitalSericeBean;
import com.tianyuan.bean.HospitalSericeBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityDrop;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.HospitalSericeRepository;

/**
 * 医院服务控制器
 * @author Administrator
 *
 */
public class HospitalSericeController extends BaseHospitalController {

	@Autowired
	HospitalSericeRepository hospitalSericeRepository;
	
	
	@GetMapping("hospitalserice/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		HospitalSericeBean entity = new HospitalSericeBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/hospitalserice/index");
		return model;
	}
	
	@PostMapping("hospitalserice/list")
	public EntityPager<HospitalSericeBean> list(int page,int rows,String status ,String key){
		String where = "";
		EntityPager<HospitalSericeBean> list = hospitalSericeRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("hospitalserice/edit")
	public ModelAndView edit(String id) {
		HospitalSericeBean entity = hospitalSericeRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new HospitalSericeBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/hospitalserice/edit");
		return model;
	}
	
	@PostMapping("hospitalserice/edit")
	public AjaxResult edit(HospitalSericeBean entity) {
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
        if (hospitalSericeRepository.exits("id<>"+entity.getId()+" and parentingway = "+entity.getParentingway()+" and parentingname='"+entity.getParentingname()+"' ")) return onFailed("服务已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) hospitalSericeRepository.insertRow(entity);
        	else hospitalSericeRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("hospitalserice/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 hospitalSericeRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
	
	//托养方式
	@GetMapping("hospitalserice/type")
	public List<EntityDrop> userGender(){
		List<EntityDrop> list = new ArrayList<EntityDrop>();
		list.add(new EntityDrop("0", " "));
		list.add(new EntityDrop("1", "寄宿托养"));
		list.add(new EntityDrop("2", "日间照料托养"));
		list.add(new EntityDrop("3","居家托养"));
		return list;
	}		
}
