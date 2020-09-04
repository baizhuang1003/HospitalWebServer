package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.ServiceBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class ServiceRepository implements EntityRepository<ServiceBean> {
	
	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(ServiceBean t) {
		// TODO Auto-generated method stub
		String sql =" INSERT INTO t_service_items" + 
				"(code, nerseryid, regcode,name, fee, status, remark, createuid, createtime) " + 
				"VALUES ("+t.getCode()+", "+t.getNerseryid()+",'"+t.getOrgcode()+"', '"+t.getName()+"', '"+t.getFee()+"', "+t.getStatus()+", '"+t.getRemark()+"', '"+t.getCreateuid()+"', now() ); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(ServiceBean t) {
		// TODO Auto-generated method stub
		String sql =" UPDATE t_service_items " + 
				"SET code = "+t.getCode()+" , nerseryid = "+t.getNerseryid()+",regcode ='"+t.getOrgcode()+"' ,name = '"+t.getName()+"', fee = '"+t.getFee()+"', status = "+t.getStatus()+", remark = '"+t.getRemark()+"', updateuid = '"+t.getUpdateuid()+"', updatetime = now() " + 
				"WHERE id="+t.getId(); 
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
	public ServiceBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_service_items where " + where;
		return ef.query(sql, new EntityMapper<ServiceBean>() {

			@Override
			public ServiceBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ServiceBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_service_items where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<ServiceBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_service_items ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<ServiceBean>() {

			@Override
			public ServiceBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ServiceBean.class);
			}

		});
	}

	@Override
	public EntityPager<ServiceBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_service_items", "*", where, order, pageindex, pagesize,
				new EntityMapper<ServiceBean>() {

				@Override
				public ServiceBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, ServiceBean.class);
				}
			});
	}
	
	public EntityPager<Map<String, Object>> pageSelectAll(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		String filename = " (SELECT a.* ,b.name AS nurseryname FROM t_service_items AS a " + 
				"LEFT JOIN t_nursery  AS b ON b.id= a.nerseryid) as c ";
		return ef.queryForPageList(filename, "*",where,order,pageindex, pagesize);
	}

}
