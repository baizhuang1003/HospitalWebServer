package com.tianyuan.bean;

/**
 * 权限表
 * @author Administrator
 *
 */
public class LimitBean {

	private int id;//权限id
	private int sort;//排序
	private int parentid;//上一级id
	private String name;//权限名称
	private String url;//路径
	private int isspecial;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsspecial() {
		return isspecial;
	}
	public void setIsspecial(int isspecial) {
		this.isspecial = isspecial;
	}
	
	
}
