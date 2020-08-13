package com.tianyuan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyuan.bean.DictionariesBean;
import com.tianyuan.core.EntityBinder;
import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.core.EntityMapper;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.EntityRepository;

public class DictionariesRepository implements EntityRepository<DictionariesBean>{
	@Autowired
	EntityFreamwork ef;

	@Override
	public boolean insertRow(DictionariesBean t) {
		// TODO Auto-generated method stub
		String sql=" INSERT INTO t_dictionaries" + 
				"(pid, type, dataname, datavalue, sort, startusing, fixation, datadescription, createuid, createtime, remark) " + 
				"VALUES ("+t.getPid()+", '"+t.getType()+"', '"+t.getDataname()+"', '"+t.getDatavalue()+"', "+t.getSort()+", "+t.getStartusing()+", "+t.getFixation()+", '"+t.getDatadescription()+"', "+t.getCreateuid()+", now(),  '"+t.getRemark()+"'); ";
		return ef.update(sql);
	}

	@Override
	public boolean updateRow(DictionariesBean t) {
		// TODO Auto-generated method stub
		String sql=" UPDATE t_dictionaries " + 
				"SET pid = "+t.getPid()+" , type = '"+t.getType()+"', dataname = '"+t.getDataname()+"', datavalue = '"+t.getDatavalue()+"', sort = "+t.getSort()+", startusing = "+t.getStartusing()+", fixation = "+t.getFixation()+", datadescription = '"+t.getDatadescription()+"', updateuid = "+t.getUpdateuid()+", updatetime = now(), remark = '"+t.getRemark()+"' " + 
				"WHERE id = "+t.getId();
		return ef.update(sql);
	}

	@Override
	public boolean deleteRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return false;
		String sql = "delete from t_dictionaries where " + where;
		return ef.update(sql);
	}

	@Override
	public DictionariesBean selectRow(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return null;
		String sql = "select * from t_dictionaries where " + where;
		return ef.query(sql, new EntityMapper<DictionariesBean>() {

			@Override
			public DictionariesBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, DictionariesBean.class);
			}

		});
	}

	@Override
	public boolean exits(String where) {
		// TODO Auto-generated method stub
		if (where == null || where.equals(""))
			return true;
		String sql = "select count(*) as c from t_dictionaries where " + where;
		return ef.exits(sql);
	}

	@Override
	public List<DictionariesBean> selectAll(String where, String order) {
		// TODO Auto-generated method stub
		String sql = "select * from t_dictionaries ";
		if (where != null && !where.equals(""))
			sql += " where " + where;
		if (order != null && !order.equals(""))
			sql += " order by " + order;
		
		return ef.queryForList(sql, new EntityMapper<DictionariesBean>() {

			@Override
			public DictionariesBean Mapper(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return EntityBinder.entityBinder(rs, DictionariesBean.class);
			}

		});
	}

	@Override
	public EntityPager<DictionariesBean> pageSelect(int pageindex, int pagesize, String where, String order) {
		// TODO Auto-generated method stub
		return ef.queryEntityForPageList("t_dictionaries", "*", where, order, pageindex, pagesize,
				new EntityMapper<DictionariesBean>() {

				@Override
				public DictionariesBean Mapper(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					return EntityBinder.entityBinder(rs, DictionariesBean.class);
				}
			});
	}

}
