package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.NurseryBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityDrop;
import com.tianyuan.core.EntityPager;
import com.tianyuan.model.UserOverload;
import com.tianyuan.repository.NurseryRepository;

@RestController
public class NurseryManageController extends BaseManageController {

	@Autowired
	NurseryRepository nurseryRepository;
	
	private final String moduleid = "100101";
	
	@GetMapping("nursery/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		this.OverloadMemberLimit(moduleid);
		UserOverload entity = new UserOverload(this);
		model.addObject("entity", entity);
		model.setViewName(prefix+"/nursery/index");
		return model;
	}
	
	@PostMapping("nursery/list")
	public EntityPager<NurseryBean> list(int page,int rows,String pid){
		Initialize();
		String where ="orgcode ='"+getCode()+"' ";
        EntityPager<NurseryBean> list = nurseryRepository.pageSelect(page, rows, where, "id");
        return list;
	}
	
	@GetMapping("nursery/edit")
	public ModelAndView edit(int id) {
		Initialize();
		NurseryBean entity = nurseryRepository.selectRow(" id="+id);
		if(entity==null) entity = new NurseryBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/nursery/edit");
		return model;
	}
	
	@PostMapping("nursery/edit")
	public AjaxResult edit(NurseryBean entity) {
		Initialize();
        if (nurseryRepository.exits("id<>"+entity.getId()+" and name='"+entity.getName()+"'")) return onFailed("托养方式已存在");
        entity.setOrgcode(getCode());
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
			nurseryRepository.deleteRow(" id='"+id+"'");
			return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
	
	@GetMapping("nursery/dictlist")
	public List<EntityDrop> dictList(){
		String where = "1=1";
		 List<NurseryBean> list = nurseryRepository.selectAll(where, "");
         List<EntityDrop> result = new ArrayList<EntityDrop>();
         for (NurseryBean item : list) {
        	 EntityDrop model = new EntityDrop();
        	 model.setId(item.getId()+"");
        	 model.setText(item.getName());
             result.add(model);
         }
         return result;
	}
}
