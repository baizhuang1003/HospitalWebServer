package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.RegionBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;



public class RegionRepository implements EntityRepository<RegionBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(RegionBean t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_region " + 
				"(id, level, code, name, province, city, zone, town, village)  " + 
				"VALUES ("+t.getId()+", "+t.getLevel()+", '"+t.getCode()+"', '"+t.getName()+"', '"+t.getProvince()+"', '"+t.getCity()+"', "
				+ "'"+t.getZone()+"', '"+t.getTown()+"', '"+t.getVillage()+"')";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(RegionBean t) {
		// TODO Auto-generated method stub
		String sql = "UPDATE t_region " + 
				"SET level = "+t.getLevel()+", code = '"+t.getCode()+"', name = '"+t.getName()+"', province = '"+t.getProvince()+"', "
				+ "city = '"+t.getCity()+"', zone = '"+t.getZone()+"', town = '"+t.getTown()+"', village = '"+t.getVillage()+"' " + 
				"WHERE id = "+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_region where " + where;
		return ef.update(sql);
	}

	@Override
	public RegionBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_region where " + where;
		return ef.query(sql, new EntityMapper<RegionBean>() {

			@Override
			public RegionBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RegionBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_region where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<RegionBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_region ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		return ef.queryForList(sql, new EntityMapper<RegionBean>() {

			@Override
			public RegionBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RegionBean.class);
			}

		});
	}

	@Override
	public EntityPager<RegionBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_region", "*", where, order, pageindex, pagesize,
			new EntityMapper<RegionBean>() {

			@Override
			public RegionBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, RegionBean.class);
			}
		});
	}
}
