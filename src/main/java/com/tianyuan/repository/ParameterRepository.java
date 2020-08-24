package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.ParameterBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class ParameterRepository implements EntityRepository<ParameterBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(ParameterBean t) {
		// TODO Auto-generated method stub
		String sql =" INSERT INTO t_parameter" + 
				"(sort, name, status, createuid, createtime) " + 
				"VALUES ("+t.getSort()+", '"+t.getName()+"', "+t.getStatus()+", '"+t.getCreateuid()+"', now()); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(ParameterBean t) {
		// TODO Auto-generated method stub
		String sql =" UPDATE t_parameter " + 
				"SET sort = "+t.getSort()+" , name = '"+t.getName()+"', status = "+t.getStatus()+",  updateuid = '"+t.getUpdateuid()+"', updatetime = now() " + 
				"WHERE id= "+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_parameter where " + where;
		return ef.update(sql);
	}

	@Override
	public ParameterBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_parameter where " + where;
		return ef.query(sql, new EntityMapper<ParameterBean>() {

			@Override
			public ParameterBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ParameterBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_parameter where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<ParameterBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_parameter ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<ParameterBean>() {

			@Override
			public ParameterBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ParameterBean.class);
			}

		});
	}

	@Override
	public EntityPager<ParameterBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_parameter", "*", where, order, pageindex, pagesize,
				new EntityMapper<ParameterBean>() {

				@Override
				public ParameterBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, ParameterBean.class);
				}
			});
	}

}
