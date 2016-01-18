package com.scsb.bean;

public class Phone {
	private int id = 0;
	private String number = null;
	private String description = null;
	private String creatTime = null;
	private String group = null;

	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
}
