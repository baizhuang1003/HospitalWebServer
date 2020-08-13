package com.tianyuan.core;

/**
 * EasyUI DropModel
 * @author Administrator
 *
 */
public class EntityDrop {
	private String id;
	private String text;
	public EntityDrop() {
		
	}
	public EntityDrop(String id,String text) {
		this.id = id;
		this.text=text;
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
	
}
