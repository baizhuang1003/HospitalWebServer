package com.tianyuan.core;

import java.util.List;
/**
 * 公告接口
 * @author Administrator
 *
 * @param <T>
 */
public interface EntityRepository<T> {

	public boolean insertRow(T t);
	public boolean updateRow(T t);
	public boolean deleteRow(String where);
	public T selectRow(String where);
	public boolean exits(String where);
	public List<T> selectAll(String where,String order);
	public EntityPager<T> pageSelect(int pageindex,int pagesize,String where,String order);
}
