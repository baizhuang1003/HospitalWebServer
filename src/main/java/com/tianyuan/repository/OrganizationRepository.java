package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class OrganizationRepository implements EntityRepository<OrganizationBean> {
	@Autowired
	EntityFreamwork ef;

	@Override
	public boolean insertRow(OrganizationBean t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_organization " + 
				"(id, code, name, abbr, regionid, region, address, phone, principal, mobile, fax, createtime, createuid, updatetime, updateuid)  " + 
				"VALUES ('"+ef.getId()+"', '"+t.getCode()+"', '"+t.getName()+"', '"+t.getAbbr()+"', '"+t.getRegionid()+"', '"+t.getRegion()+"', "
				+ "'"+t.getAddress()+"', '"+t.getPhone()+"', '"+t.getPrincipal()+"', '"+t.getMobile()+"', '"+t.getFax()+"', now(), "
				+ "'"+t.getCreateuid()+"', now(), '"+t.getUpdateuid()+"')";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(OrganizationBean t) {
		// TODO Auto-generated method stub
		String sql = "UPDATE t_organization " + 
				"SET code = '"+t.getCode()+"', name = '"+t.getName()+"', abbr = '"+t.getAbbr()+"', regionid = '"+t.getRegionid()+"', region = '"+t.getRegion()+"', "
				+ "address = '"+t.getAddress()+"', phone = '"+t.getPhone()+"', principal = '"+t.getPrincipal()+"', mobile = '"+t.getMobile()+"', "
				+ "fax = '"+t.getFax()+"', createtime = now(), createuid = '"+t.getCreateuid()+"', "
				+ "updatetime = now(), updateuid = '"+t.getUpdateuid()+"' " + 
				"WHERE id = '"+t.getId()+"'";
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_organization where " + where;
		return ef.update(sql);
	}

	@Override
	public OrganizationBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_organization where " + where;
		return ef.query(sql, new EntityMapper<OrganizationBean>() {

			@Override
			public OrganizationBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, OrganizationBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_organization where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<OrganizationBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_organization ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<OrganizationBean>() {

			@Override
			public OrganizationBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, OrganizationBean.class);
			}

		});
	}

	@Override
	public EntityPager<OrganizationBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_organization", "*", where, order, pageindex, pagesize,
			new EntityMapper<OrganizationBean>() {

			@Override
			public OrganizationBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, OrganizationBean.class);
			}
		});
	}
	
	public OrganizationBean SelectRowByCode(String code) {
		// TODO Auto-generated method stub
		String sql = "select * from t_organization where code='" + code + "'";
		return ef.query(sql, new EntityMapper<OrganizationBean>() {

			@Override
			public OrganizationBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, OrganizationBean.class);
			}

		});
	}

}
