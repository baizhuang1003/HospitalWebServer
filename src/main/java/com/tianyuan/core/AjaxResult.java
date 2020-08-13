package com.tianyuan.core;

public class AjaxResult {
	private int code;
	private Object content;
	private String message;
	
	public AjaxResult() {}
	public AjaxResult(int code,String msg) {
		this.code=code;
		this.message = msg;
	}
	public AjaxResult(int code,Object obj,String msg) {
		this.code=code;
		this.content = obj;
		this.message=msg;
	}
	

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
