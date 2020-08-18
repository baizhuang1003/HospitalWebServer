package com.tianyuan.bean;

import java.util.Date;

/**
 * 短信表
 * @author Administrator
 *
 */
public class SmsBean {

	private String id;
	private String mobile;
	private String code;
	private Date createtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
