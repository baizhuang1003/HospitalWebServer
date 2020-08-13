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
		String sql =" INSERT INTO t_organization" + 
				"(region, organization, abbreviation, synopsis, site, website, phone, email, certificationstatus, businesslicense, status, createuid, createtime,  remark) " + 
				"VALUES ('"+t.getRegion()+"', '"+t.getOrganization()+"', '"+t.getAbbreviation()+"', '"+t.getSynopsis()+"', '"+t.getSite()+"', '"+t.getWebsite()+"', '"+t.getPhone()+"', '"+t.getEmail()+"',"
						+ " "+t.getCertificationstatus()+", '"+t.getBusinesslicense()+"', "+t.getStatus()+", "+t.getCreateuid()+", now(), '"+t.getRemark()+"'); ";
		System.out.println(sql);
		return ef.update(sql);
		
	}

	@Override
	public boolean updateRow(OrganizationBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_organization " + 
				"SET region = '"+t.getRegion()+"' , organization = '"+t.getOrganization()+"', abbreviation = '"+t.getAbbreviation()+"', synopsis = '"+t.getSynopsis()+"', site = '"+t.getSite()+"', website = '"+t.getWebsite()+"',"
						+ " phone = '"+t.getPhone()+"', email = '"+t.getEmail()+"', certificationstatus = "+t.getCertificationstatus()+", businesslicense = '"+t.getBusinesslicense()+"', status = "+t.getStatus()+",  updateuid = "+t.getUpdateuid()+", updatetime = now(), remark = '"+t.getRemark()+"',audituser='"+t.getAudituser()+"' " + 
				"WHERE id = "+t.getId();
		System.out.println(sql);
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
		System.out.println(sql);
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

}
