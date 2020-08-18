package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.UserBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class UserRepository implements EntityRepository<UserBean> {

	@Autowired
	EntityFreamwork ef;
	@Override
	public boolean insertRow(UserBean t) {
		// TODO Auto-generated method stub
		String sql=" INSERT INTO t_user" + 
				"(id, orgcode, name, sex, mobile, email, avatar, username, password, deptid, roleid, ismanager, createuid, createtime, updateuid, updatetime) " + 
				"VALUES ('"+ef.getId()+"', '"+t.getOrgcode()+"', '"+t.getName()+"', "+t.getSex()+", '"+t.getMobile()+"', '"+t.getEmail()+"', '"+t.getAvatar()+"', '"+t.getUsername()+"', '"+t.getPassword()+"', "+t.getDeptid()+", '"+t.getRoleid()+"', "+t.getIsmanager()+", '"+t.getCreateuid()+"', now());" + 
				" ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(UserBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_user " + 
				"SET  orgcode = '"+t.getOrgcode()+"', name = '"+t.getName()+"', sex = "+t.getSex()+", mobile = '"+t.getMobile()+"', email = '"+t.getEmail()+"', avatar = '"+t.getAvatar()+"', username = '"+t.getUsername()+"', password = '"+t.getPassword()+"', deptid = "+t.getDeptid()+", roleid = '"+t.getRoleid()+"', ismanager = "+t.getIsmanager()+", updateuid = '"+t.getUpdateuid()+"', updatetime = now() " + 
				"WHERE id = '"+t.getId()+"' ";
		return ef.update(sql);
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
	public UserBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_user where " + where;
		return ef.query(sql, new EntityMapper<UserBean>() {

			@Override
			public UserBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, UserBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_member where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<UserBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_member ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<UserBean>() {

			@Override
			public UserBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, UserBean.class);
			}

		});
	}

	@Override
	public EntityPager<UserBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_member", "*", where, order, pageindex, pagesize,
				new EntityMapper<UserBean>() {

				@Override
				public UserBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, UserBean.class);
				}
			});
	}

}
