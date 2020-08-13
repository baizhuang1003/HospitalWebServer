package com.tianyuan.bean;

import java.util.Date;

/**
 * 字典信息
 * @author Administrator
 *
 */
public class DictionariesBean {

	private int id,pid,sort,startusing,fixation,createuid,updateuid;
	
	private String type,dataname,datavalue,datadescription,remark;
	
	private Date createtime,updatetime;

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

	public int getStartusing() {
		return startusing;
	}

	public void setStartusing(int startusing) {
		this.startusing = startusing;
	}

	public int getFixation() {
		return fixation;
	}

	public void setFixation(int fixation) {
		this.fixation = fixation;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}

	public String getDatadescription() {
		return datadescription;
	}

	public void setDatadescription(String datadescription) {
		this.datadescription = datadescription;
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
