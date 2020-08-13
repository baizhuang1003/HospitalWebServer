package com.tianyuan.bean;

import java.util.Date;

public class ServiceItemsBean {

	private int id ,code,createuid,updateuid;
	private String name,remark;
	private Date createtime,updatetime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public int getCreateuid() {
		return createuid;
	}
	public void setCreateuid(int createuid) {
		this.createuid = createuid;
	}
	public int getUpdateuid() {
		return updateuid;
	}
	public void setUpdateuid(int updateuid) {
		this.updateuid = updateuid;
	}
	
	
	
}
