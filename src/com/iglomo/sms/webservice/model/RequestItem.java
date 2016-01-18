package com.iglomo.sms.webservice.model;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement(name = "ITEM")
@XmlType(propOrder = { "schedule", "isMultiple", "message", "callee" })
public class RequestItem {
	
	@NotBlank(message="不得為空")
	private String callee;
	
	@NotBlank(message="不得為空")
	private String isMultiple="0";
	
	@NotBlank(message="不得為空")
	private String message="";
	
	public RequestItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//@Pattern(message = "0為即時發送；指定時間, 格式為YYYY/MM/DD HH24:MI:SS,",regexp="0|([2][0-1][0-9][0-9]/(0?[1-9]|1[012]/[1-9]|[12][0-9]|3[01]))")
	@NotBlank(message="不得為空")
	private String schedule = "0";

	@XmlElement(name = "PHONE")
	public String getCallee() {
		return callee;
	}

	@XmlElement(name = "MULTIPLE")
	public String getIsMultiple() {
		return isMultiple;
	}

	@XmlElement(name = "MSG")
	public String getMessage() {
		return message;
	}

	@XmlElement(name = "SCHEDULE")
	public String getSchedule() {
		return schedule;
	}

	/**
	 * @param calleeList the calleeList to set
	 */
	public void setCallee(String callee) {
		this.callee = callee;
	}

	/**
	 * @param isMultiple the isMultiple to set
	 */
	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	
	

}
