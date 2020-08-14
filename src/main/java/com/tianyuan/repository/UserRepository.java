package com.tianyuan.repository;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.Userbean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class UserRepository implements EntityRepository<Userbean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(Userbean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRow(Userbean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_user where " + where;
		return ef.update(sql);
	}

	@Override
	public Userbean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_user where " + where;
		return ef.query(sql, new EntityMapper<Userbean>() {

			@Override
			public Userbean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, Userbean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_user where " + where;
		System.out.println(sql);
		return ef.exits(sql);
	}

	@Override
	public List<Userbean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_user ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<Userbean>() {

			@Override
			public Userbean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, Userbean.class);
			}

		});
	}

	@Override
	public EntityPager<Userbean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_user", "*", where, order, pageindex, pagesize,
				new EntityMapper<Userbean>() {

				@Override
				public Userbean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, Userbean.class);
				}
			});
	}

}
