package com.tianyuan.bean;

import java.util.Date;

/**
 * 机构设置
 * @author Administrator
 *
 */
public class OrganizationBean {
	private String id;
	private String code;
	private String name;
	private String abbr;
	private String regionid;
	private String region;
	private String address;
	private String phone;
	private String principal;
	private String mobile;
	private String fax;
	
	private Date createtime;
	private String createuid;
	private Date updatetime;
	private String updateuid;
	
	private String email,businesslicense,audituser,remark;
	
	private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBusinesslicense() {
		return businesslicense;
	}
	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}
	public String getAudituser() {
		return audituser;
	}
	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
