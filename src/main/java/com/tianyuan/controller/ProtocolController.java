package com.tianyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.ProtocolBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.EntityPager;
import com.tianyuan.repository.ProtocolRepository;

/**
 * 服务协议管理
 * @author Administrator
 *
 */
@RestController
public class ProtocolController extends BaseHospitalController {

	@Autowired
	ProtocolRepository protocolRepository;
	
	@GetMapping("protocol/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		ProtocolBean entity = new ProtocolBean();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/protocol/index");
		return model;
	}
	
	@PostMapping("protocol/list")
	public EntityPager<ProtocolBean> list(int page,int rows){
		String where = "1=1";
		EntityPager<ProtocolBean> list = protocolRepository.pageSelectAll(page,rows,where,"");
		return list;
	}
	
	@GetMapping("protocol/edit")
	public ModelAndView edit(int id) {
		ProtocolBean entity = protocolRepository.selectRow(" id="+id);
		if(entity==null) entity = new ProtocolBean();
		ModelAndView model = new ModelAndView();
		model.addObject("entity", entity);
		model.setViewName(prefix+"/protocol/edit");
		return model;
	}
	
	@PostMapping("protocol/edit")
	public AjaxResult edit(ProtocolBean entity) {
        if (protocolRepository.exits("id<>"+entity.getId()+" and nurseryid='"+entity.getNurseryid()+"' and name='"+entity.getName()+"'")) return onFailed("协议已存在");
        System.out.println(entity.getId());
        try {
        	if (entity.getId()<1) protocolRepository.insertRow(entity);
        	else protocolRepository.updateRow(entity);
        	return onSuccess("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("保存失败");
		}
	}
	
	@PostMapping("protocol/delete")
	public AjaxResult delete(String id) {
		 if (id==null || id.equals("")) return onFailed("参数错误");
		 try {
			 protocolRepository.deleteRow("id="+id);
			 return onSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("删除失败");
		}
	}
}
