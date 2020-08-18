package com.tianyuan.bean;

import java.util.Date;

/**
 * 用户表
 * @author Administrator
 *
 */
public class UserBean {

	private int sex,deptid,ismanager;
	private String id,orgcode,name,mobile,email,avatar,username,password,roleid,createuid,updateuid;
	private Date createtime,updatetime;
	
	
	public int getIsmanager() {
		return ismanager;
	}
	public void setIsmanager(int ismanager) {
		this.ismanager = ismanager;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getCreateuid() {
		return createuid;
	}
	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}
	public String getUpdateuid() {
		return updateuid;
	}
	public void setUpdateuid(String updateuid) {
		this.updateuid = updateuid;
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
