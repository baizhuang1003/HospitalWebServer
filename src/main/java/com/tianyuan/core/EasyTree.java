package com.tianyuan.core;

import java.util.List;

//easyui tree 模型
public class EasyTree {
	private String id;
	private String text;
	private String iconCls;
	private String state="open";
	private List<EasyTree> children;
	private boolean checked =false;
	private String attributes;
	public EasyTree() {
		
	}
	public EasyTree(String id,String text,String iconCls) {
		this.id = id;
		this.text = text;
		this.iconCls=iconCls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<EasyTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyTree> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
}
