package com.tianyuan.model;

import java.util.List;

import com.tianyuan.bean.DeptBean;
import com.tianyuan.bean.LimitBean;
import com.tianyuan.bean.OrganizationBean;
import com.tianyuan.bean.UserBean;

public class UserModel {
	
    private UserBean user;
    private OrganizationBean organ;
    private DeptBean dept;
    private String systemname;
    private List<LimitBean> limits;
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public OrganizationBean getOrgan() {
		return organ;
	}
	public void setOrgan(OrganizationBean organ) {
		this.organ = organ;
	}
	public DeptBean getDept() {
		return dept;
	}
	public void setDept(DeptBean dept) {
		this.dept = dept;
	}
	public String getSystemname() {
		return systemname;
	}
	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}
	public List<LimitBean> getLimits() {
		return limits;
	}
	public void setLimits(List<LimitBean> limits) {
		this.limits = limits;
	}
    
}
