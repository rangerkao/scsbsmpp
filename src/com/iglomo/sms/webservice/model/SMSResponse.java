package com.iglomo.sms.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SMSRESPONSE")
@XmlType(propOrder = { "username", "orgcode", "uid", "responseItemList" })
public class SMSResponse {
	
	private String username = "";
		
	private String orgcode = "";
	
	private String uid = "";

	private List<ResponseItem> responseItemList = new ArrayList<ResponseItem>();
	
	@XmlElementWrapper(name = "DATA")
	@XmlElement(name = "ITEM")
	public List<ResponseItem> getResponseItemList() {
		return responseItemList;
	}
	
	@XmlElement(name = "UID")
	public String getUid() {
		return uid;
	}
	
	@XmlElement(name = "ORGCODE")
	public String getOrgcode() {
		return orgcode;
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


	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @param responseItemList the responseItemList to set
	 */
	public void setResponseItemList(List<ResponseItem> responseItemList) {
		this.responseItemList = responseItemList;
	}

}
