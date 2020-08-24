package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.RoleBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class RoleRepository implements EntityRepository<RoleBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(RoleBean t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_role " + 
				"(id, orgcode, name, limits, remark, createtime, createuid, updatetime, updateuid)  " + 
				"VALUES ('"+ef.getId()+"', '"+t.getOrgcode()+"', '"+t.getName()+"', '"+t.getLimits()+"', '"+t.getRemark()+"', "
				+ "now(), '"+t.getCreateuid()+"', now(), '"+t.getUpdateuid()+"')";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(RoleBean t) {
		// TODO Auto-generated method stub
		String sql = "UPDATE t_role  " + 
				"SET orgcode = '"+t.getOrgcode()+"', name = '"+t.getName()+"', limits = '"+t.getLimits()+"', remark = '"+t.getRemark()+"', "
				+ "createtime = now(), createuid = '"+t.getCreateuid()+"', updatetime = now(), "
				+ "updateuid = '"+t.getUpdateuid()+"'  " + 
				"WHERE id = '"+t.getId()+"'";
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_role where " + where;
		return ef.update(sql);
	}

	@Override
	public RoleBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_role where " + where;
		return ef.query(sql, new EntityMapper<RoleBean>() {

			@Override
			public RoleBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RoleBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_role where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<RoleBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_role ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<RoleBean>() {

			@Override
			public RoleBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RoleBean.class);
			}

		});
	}

	@Override
	public EntityPager<RoleBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_role", "*", where, order, pageindex, pagesize,
			new EntityMapper<RoleBean>() {

			@Override
			public RoleBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RoleBean.class);
			}
		});
	}

}
