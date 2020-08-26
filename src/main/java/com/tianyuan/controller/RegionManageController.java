package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianyuan.bean.RegionBean;
import com.tianyuan.core.EntityDrop;
import com.tianyuan.repository.RegionRepository;

@RestController
public class RegionManageController  extends BaseManageController{

	@Autowired
	RegionRepository regionRepository;
	
	
	@GetMapping("region/sexlist")
	public List<EntityDrop> sexList(){
		 List<EntityDrop> list = new ArrayList<EntityDrop>();
         list.add(new EntityDrop("0",""){});
         list.add(new EntityDrop("1","男") {});
         list.add(new EntityDrop("2","女") {});
         return list;
	}
	
	/**
	 * 获取地址列表
	 * @param id
	 * @return
	 */
	@GetMapping("region/list")
	public List<EntityDrop> list(String id){
		if(id==null) id="";
		List<EntityDrop> list = new ArrayList<EntityDrop>();
		EntityDrop drop = new EntityDrop();
		drop.setId("000000");
		drop.setText("");
        list.add(drop);
		List<RegionBean> temp = regionRepository.selectAll(getWhere(id), "code asc");
        for (RegionBean item : temp){
        	EntityDrop entity = new EntityDrop();
        	entity.setId(item.getCode().substring(0,6));
        	entity.setText(item.getName());
            list.add(entity);
        }
        return list;
	}
	
	private String getWhere(String id) {
        String[] codes = getCode(id);
        String where = "";
        if (codes[0].equals("00"))
            where = "province <>'00' and city ='00'";
        else
        {
            if (codes[1].equals("00"))
                where = "province ='" + codes[0] + "' and city <>'00' and zone = '00'";
            else
            {
                where = "province ='" + codes[0] + "' and city ='" + codes[1] + "' and zone <> '00'";
            }
        }
        return where;
    }
	
	private String[] getCode(String id) {
        if (id==null || id.equals("")) return new String[] { "00", "00", "00" };
        if (id.length() != 6)
            return new String[] { "00", "00", "00" };

        return new String[] { id.substring(0, 2), id.substring(2, 4), id.substring(4, 6) };
    }
}
