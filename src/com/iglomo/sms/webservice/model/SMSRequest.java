package com.iglomo.sms.webservice.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "SMSREQUEST")
@XmlType(propOrder = { "username", "passowrd", "orgcode", "requestItemList", "remark" })
public class SMSRequest {
	
	@NotBlank(message="不得為空")
	private String username = "";
	
	@NotBlank(message="不得為空")
	private String passowrd = "";
	
	@NotBlank(message="不得為空")
	private String orgcode = "";

	@NotEmpty(message="At least one passenger is required")
	@Valid
	private List<RequestItem> requestItemList;

	private String remark = "";

	
	@XmlElementWrapper(name = "DATA")
	@XmlElement(name = "ITEM")
	public List<RequestItem> getRequestItemList() {
		return requestItemList;
	}

	@XmlElement(name = "ORGCODE")
	public String getOrgcode() {
		return orgcode;
	}

	@XmlElement(name = "PASSWORD")
	public String getPassowrd() {
		return passowrd;
	}

	@XmlElement(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	@XmlElement(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	/**
	 * @param oRGCODE
	 *            the oRGCODE to set
	 */
	public void setOrgcode(String oRGCODE) {
		orgcode = oRGCODE;
	}

	/**
	 * @param passowrd
	 *            the passowrd to set
	 */
	@NotBlank(message="不得為空")
	@Size(min=6, max=20, message="長度需介於{min}與{max}之間")
	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param smsItemList the smsItemList to set
	 */
	public void setRequestItemList(List<RequestItem> smsItemList) {
		this.requestItemList = smsItemList;
	}

}
