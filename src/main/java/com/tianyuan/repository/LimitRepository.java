package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.LimitBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class LimitRepository implements EntityRepository<LimitBean> {
	@Autowired
	EntityFreamwork ef;

	@Override
	public boolean insertRow(LimitBean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRow(LimitBean t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LimitBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_limit where " + where;
		return ef.query(sql, new EntityMapper<LimitBean>() {

			@Override
			public LimitBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, LimitBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LimitBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPager<LimitBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

}
