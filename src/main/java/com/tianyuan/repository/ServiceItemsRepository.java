package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.ServiceItemsBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class ServiceItemsRepository implements EntityRepository<ServiceItemsBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(ServiceItemsBean t) {
		// TODO Auto-generated method stub
		String sql=" INSERT INTO t_service_items" + 
				"( code, name, remark, createuid, createtime) " + 
				"VALUES ("+t.getCode()+", '"+t.getName()+"', '"+t.getRemark()+"', "+t.getCreateuid()+", now()); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(ServiceItemsBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_service_items " + 
				"SET   code = "+t.getCode()+", name = '"+t.getName()+"', remark = '"+t.getRemark()+"', updateuid = "+t.getUpdateuid()+", updatetime = now() " + 
				"WHERE  id ="+ t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_service_items where " + where;
		return ef.update(sql);
	}

	@Override
	public ServiceItemsBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_service_items where " + where;
		return ef.query(sql, new EntityMapper<ServiceItemsBean>() {

			@Override
			public ServiceItemsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ServiceItemsBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_service_items where " + where;
		System.out.println(sql);
		return ef.exits(sql);
	}

	@Override
	public List<ServiceItemsBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_service_items ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<ServiceItemsBean>() {

			@Override
			public ServiceItemsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ServiceItemsBean.class);
			}

		});
	}

	@Override
	public EntityPager<ServiceItemsBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_service_items", "*", where, order, pageindex, pagesize,
				new EntityMapper<ServiceItemsBean>() {

				@Override
				public ServiceItemsBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, ServiceItemsBean.class);
				}
			});
	}

}
