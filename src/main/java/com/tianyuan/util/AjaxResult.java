package com.tianyuan.util;

public class AjaxResult {
	private String state;
	private Object content;
	
	public AjaxResult(String state,Object content) {
		this.state=state;
		this.content=content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}


	

	
}
