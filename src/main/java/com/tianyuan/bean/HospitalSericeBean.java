package com.tianyuan.bean;

import java.util.Date;

/**
    * 医院服务项目表
 * @author Administrator
 *
 */
public class HospitalSericeBean {

	private int id,parentingway,cost,state,createuid,updateuid;
	
	private String parentingname,sericeserial,remark;
	
	private Date createtime,updatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentingway() {
		return parentingway;
	}

	public void setParentingway(int parentingway) {
		this.parentingway = parentingway;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public String getParentingname() {
		return parentingname;
	}

	public void setParentingname(String parentingname) {
		this.parentingname = parentingname;
	}

	public String getSericeserial() {
		return sericeserial;
	}

	public void setSericeserial(String sericeserial) {
		this.sericeserial = sericeserial;
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
	
	
	
	
}
