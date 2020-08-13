package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.HospitalSericeBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class HospitalSericeRepository implements EntityRepository<HospitalSericeBean> {
	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(HospitalSericeBean t) {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO t_hospital_serice" + 
				"(parentingway, parentingname, sericeserial, cost, state, createuid, createtime, updateuid, updatetime, remark) " + 
				"VALUES ("+t.getParentingway()+", '"+t.getParentingname()+"', '"+t.getSericeserial()+"', "+t.getCost()+", "+t.getState()+", "+t.getCreateuid()+", now(), '"+t.getRemark()+"'); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(HospitalSericeBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_hospital_serice " + 
				"SET parentingway = "+t.getParentingway()+" , parentingname = '"+t.getParentingname()+"', sericeserial = '"+t.getSericeserial()+"', cost = "+t.getCost()+", state = "+t.getState()+", updateuid = "+t.getUpdateuid()+", updatetime = now(), remark = '"+t.getRemark()+"' " + 
				"WHERE id = "+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_hospital_serice where " + where;
		return ef.update(sql);
	}

	@Override
	public HospitalSericeBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_hospital_serice where " + where;
		return ef.query(sql, new EntityMapper<HospitalSericeBean>() {

			@Override
			public HospitalSericeBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, HospitalSericeBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_hospital_serice where " + where;
		System.out.println(sql);
		return ef.exits(sql);
	}

	@Override
	public List<HospitalSericeBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_hospital_serice ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<HospitalSericeBean>() {

			@Override
			public HospitalSericeBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, HospitalSericeBean.class);
			}

		});
	}

	@Override
	public EntityPager<HospitalSericeBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_hospital_serice", "*", where, order, pageindex, pagesize,
				new EntityMapper<HospitalSericeBean>() {

				@Override
				public HospitalSericeBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, HospitalSericeBean.class);
				}
			});
	}

}
