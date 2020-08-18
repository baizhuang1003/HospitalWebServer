package com.tianyuan.repository;

import java.util.List;

import com.tianyuan.bean.RoleBean;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class RoleRepository implements EntityRepository<RoleBean> {

	@Override
	public boolean insertRow(RoleBean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRow(RoleBean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RoleBean selectRow(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPager<RoleBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

}
