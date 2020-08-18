package com.tianyuan.bean;

import java.util.Date;

/**
 * 角色表
 * @author Administrator
 *
 */
public class RoleBean {

	private String id;//角色id
	private String orgcode;//编码
	private String name;//角色名称
	private String limits;//角色权限
	private String remark;//备注
	private Date createtime;//创建时间
	private String createuid;//创建人
	private Date updatetime;//修改时间
	private String updateuid;//修改人
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
	public String getLimits() {
		return limits;
	}
	public void setLimits(String limits) {
		this.limits = limits;
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
	public String getCreateuid() {
		return createuid;
	}
	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuid() {
		return updateuid;
	}
	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
	}
	
	
}
