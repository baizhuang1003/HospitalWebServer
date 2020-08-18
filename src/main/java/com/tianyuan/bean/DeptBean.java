package com.tianyuan.bean;

import java.util.Date;

public class DeptBean {

	private String id;//部门id
	private String orgcode;//编码
	private String name;//部门姓名
	private int sort;//部门排序
	private String parentid;//上级部门
	private String remark;//备注
	private String createuid;//创建id
	private Date createtime;//创建时间
	private String updateuid;//修改id
	private Date updatetime;//修改时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateuid() {
		return createuid;
	}
	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUpdateuid() {
		return updateuid;
	}
	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
