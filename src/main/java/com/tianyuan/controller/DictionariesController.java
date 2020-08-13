package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.DictionariesBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.DictionariesRepository;

/**
 * 字典设置
 * @author Administrator
 *
 */
@RestController
public class DictionariesController extends BaseHospitalController{

	@Autowired
	DictionariesRepository dictionariesRepository;
	
	
	@GetMapping("dictionaries/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		DictionariesBean entity = new DictionariesBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dictionaries/index");
		return model;
	}
	
	@PostMapping("dictionaries/list")
	public EntityPager<DictionariesBean> list(int page,int rows,String status ,String key){
		String where = "";
		EntityPager<DictionariesBean> list = dictionariesRepository.pageSelect(page,rows,where,"");
		return list;
	}
	
	@GetMapping("dictionaries/edit")
	public ModelAndView edit(String id) {
		DictionariesBean entity = dictionariesRepository.selectRow(" id='"+id+"'");
		if(entity==null) entity = new DictionariesBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/dictionaries/edit");
		return model;
	}
	
	@PostMapping("dictionaries/edit")
	public AjaxResult edit(DictionariesBean entity) {
		entity.setCreateuid(this.getUserId());
		entity.setUpdateuid(this.getUserId());
        if (dictionariesRepository.exits("id<>"+entity.getId()+" and dataname='"+entity.getDataname()+"' ")) return onFailed("名称已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) dictionariesRepository.insertRow(entity);
        	else dictionariesRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("dictionaries/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 dictionariesRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
