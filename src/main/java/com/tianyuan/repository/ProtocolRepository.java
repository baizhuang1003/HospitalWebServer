package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.ProtocolBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class ProtocolRepository implements EntityRepository<ProtocolBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(ProtocolBean t) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO t_protocol" + 
				"( nurseryid, name, url) " + 
				"VALUES ( "+t.getNurseryid()+", '"+t.getName()+"', '"+t.getUrl()+"'); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(ProtocolBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_protocol " + 
				"SET nurseryid = "+t.getNurseryid()+", name = '"+t.getName()+"', url = '"+t.getUrl()+"' " + 
				"WHERE id = "+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_protocol where " + where;
		return ef.update(sql);
	}

	@Override
	public ProtocolBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_protocol where " + where;
		return ef.query(sql, new EntityMapper<ProtocolBean>() {

			@Override
			public ProtocolBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ProtocolBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_protocol where " + where;
		System.out.println(sql);
		return ef.exits(sql);
	}

	@Override
	public List<ProtocolBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_protocol ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<ProtocolBean>() {

			@Override
			public ProtocolBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ProtocolBean.class);
			}

		});
	}

	@Override
	public EntityPager<ProtocolBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_protocol", "*", where, order, pageindex, pagesize,
				new EntityMapper<ProtocolBean>() {

				@Override
				public ProtocolBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, ProtocolBean.class);
				}
			});
	}
	
	public EntityPager<ProtocolBean> pageSelectAll(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		//if (where!= null && !where.equals("")) where = " and " + where;
		String filed = " *,(SELECT name FROM t_nursery WHERE id=t_protocol.nurseryid) AS nurseryname ";
		return ef.queryEntityForPageList("t_protocol", filed, where, order, pageindex, pagesize,
			new EntityMapper<ProtocolBean>() {
			@Override
			public ProtocolBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, ProtocolBean.class);
			}
		});
	}

}
