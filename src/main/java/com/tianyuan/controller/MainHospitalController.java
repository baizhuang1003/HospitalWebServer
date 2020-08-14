package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.LimitBean;
import com.tianyuan.core.EasyTree;
import com.tianyuan.repository.LimitRepository;



@RestController
public class MainHospitalController extends BaseHospitalController {
	
	@Autowired
	LimitRepository limitRepository;
	
	@GetMapping("main/home")
	public ModelAndView homeView() {
		ModelAndView model = new ModelAndView();
		model.setViewName("manage/main/home");
		return model;
	}
	
	@GetMapping("main/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.addObject("name", "超级管理员");
		model.setViewName("manage/main/index");
		return model;
	}
	

		//权限列表
	@GetMapping("main/getusertree")
	public List<EasyTree> GetUserTree(){
        List<LimitBean> list = new ArrayList<LimitBean>();
        list.addAll(limitRepository.selectAll("", "sort"));
        List<EasyTree> result = new ArrayList<EasyTree>();
        result.addAll(CreateChildTree(list, 0));
        return result;
	}
	
	private List<EasyTree> CreateChildTree(List<LimitBean> list, int parentid)
    {
        List<LimitBean> temp = new ArrayList<LimitBean>();
        for (LimitBean item : list) {
			if(item.getParientid().equals(parentid)) temp.add(item);
		}
        List<EasyTree> result = new ArrayList<EasyTree>();
        for (LimitBean item : temp)
        {
            EasyTree model = new EasyTree();
            model.setAttributes(item.getAction());
            model.setId(item.getId()+"");
            model.setText(item.getName());
            model.setChildren(CreateChildTree(list,Integer.parseInt(item.getId())));
            if (item.getParientid().equals(0))
                model.setIconCls("icon-layout");
            else
            	model.setIconCls("icon-gears");
           // if (model.getChildren().size() > 0)
           //     model.setState("closed");
            result.add(model);
        }
        return result;
    }
}
