package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.DeptBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class DeptRepository implements EntityRepository<DeptBean>{
	@Autowired
	EntityFreamwork ef;

	@Override
	public boolean insertRow(DeptBean t) {
		// TODO Auto-generated method stub
		String sql=" INSERT INTO t_dept" + 
				"( parentid, deptname, sort, createtime) " + 
				"VALUES ("+t.getParentid()+", '"+t.getDeptname()+"', "+t.getSort()+", now()); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(DeptBean t) {
		// TODO Auto-generated method stub
		String sql =" UPDATE t_dept " + 
				"SET id = id , parentid = "+t.getParentid()+", deptname = '"+t.getDeptname()+"', sort = "+t.getSort()+", updatetime = now() " + 
				
				"WHERE  id="+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_dept where " + where;
		return ef.update(sql);
	}

	@Override
	public DeptBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_dept where " + where;
		return ef.query(sql, new EntityMapper<DeptBean>() {

			@Override
			public DeptBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, DeptBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_dept where " + where;
		System.out.println(sql);
		return ef.exits(sql);
	}

	@Override
	public List<DeptBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_dept ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<DeptBean>() {

			@Override
			public DeptBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, DeptBean.class);
			}

		});
	}

	@Override
	public EntityPager<DeptBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_dept", "*", where, order, pageindex, pagesize,
				new EntityMapper<DeptBean>() {

				@Override
				public DeptBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, DeptBean.class);
				}
			});
	}

}
