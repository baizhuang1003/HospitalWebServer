package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.SmsBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class SmsRepository implements EntityRepository<SmsBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(SmsBean t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_sms " + 
				"(id, mobile, code, createtime)  " + 
				"VALUES ('"+t.getId()+"', '"+t.getMobile()+"', '"+t.getCode()+"', now())";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(SmsBean t) {
		// TODO Auto-generated method stub
		String sql = "UPDATE t_sms " + 
				"SET mobile = '"+t.getMobile()+"', code = '"+t.getCode()+"', createtime = now() " + 
				"WHERE id = '"+t.getId()+"'";
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_sms where " + where;
		return ef.update(sql);
	}
	
	public boolean deleteExpired() {
		// TODO Auto-generated method stub
		String sql = "delete from t_sms where createtime <=date_add(now(),interval -5 minute)";
		return ef.update(sql);
	}

	@Override
	public SmsBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_sms where " + where;
		return ef.query(sql, new EntityMapper<SmsBean>() {

			@Override
			public SmsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, SmsBean.class);
			}

		});
	}
	
	public SmsBean selectRowByMobile(String mobile) {
		// TODO Auto-generated method stub
		String sql = "select * from t_sms where mobile='" + mobile + "' and createtime >= date_add(now(),interval -5 minute) order by createtime desc limit 1";
		return ef.query(sql, new EntityMapper<SmsBean>() {

			@Override
			public SmsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, SmsBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_sms where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<SmsBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_sms ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<SmsBean>() {

			@Override
			public SmsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, SmsBean.class);
			}

		});
	}

	@Override
	public EntityPager<SmsBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_sms", "*", where, order, pageindex, pagesize,
			new EntityMapper<SmsBean>() {

			@Override
			public SmsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, SmsBean.class);
			}
		});
	}
	
	public SmsBean SelectRowByMobile(String mobile)
    {
        String sql = "select * from t_sms where mobile='" + mobile + "' and createtime >= date_add(now(),interval -5 minute) order by createtime desc limit 1";
        
        return ef.query(sql, new EntityMapper<SmsBean>() {

			@Override
			public SmsBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, SmsBean.class);
			}

		});
    }


}
