package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.NurseryBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class NurseryRepository implements EntityRepository<NurseryBean> {

	@Autowired
	EntityFreamwork ef;
	
	@Override
	public boolean insertRow(NurseryBean t) {
		// TODO Auto-generated method stub
		String sql ="INSERT INTO t_nursery" + 
				"( name, orgcode,code, status, remark) " + 
				"VALUES ('"+t.getName()+"','"+t.getOrgcode()+"', "+t.getCode()+", "+t.getStatus()+", '"+t.getRemark()+"');";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(NurseryBean t) {
		// TODO Auto-generated method stub
		String sql =" UPDATE t_nursery " + 
				"SET  name = '"+t.getName()+"',orgcode='"+t.getOrgcode()+"', code = "+t.getCode()+", status = "+t.getStatus()+", remark = '"+t.getRemark()+"' " + 
				"WHERE  id ="+ t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_nursery where " + where;
		return ef.update(sql);
	}

	@Override
	public NurseryBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_nursery where " + where;
		return ef.query(sql, new EntityMapper<NurseryBean>() {

			@Override
			public NurseryBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, NurseryBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_nursery where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<NurseryBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_nursery ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<NurseryBean>() {

			@Override
			public NurseryBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, NurseryBean.class);
			}

		});
	}

	@Override
	public EntityPager<NurseryBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_nursery", "*", where, order, pageindex, pagesize,
				new EntityMapper<NurseryBean>() {

				@Override
				public NurseryBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, NurseryBean.class);
				}
			});
	}

}
