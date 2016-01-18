package com.scsb.bean;

public class Order {
	private int id;
	private long createTime;
	private String createTime2;
	private String createIp;
	private int spent_point;
	private int status;
	private int mode;
	private String number;
	private String req_schedule;
	private String req_multiple;
	private String req_msg;
	private String req_remark;
	private String[] req_send_number = null;
	private String res_data;
	private String res_response;
	private Smppuser smppuser;
	private String uid;
	private int area;
	private String smppuser_id;
	private String smppuser_com;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime2() {
		return createTime2;
	}
	public void setCreateTime2(String createTime2) {
		this.createTime2 = createTime2;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public int getSpent_point() {
		return spent_point;
	}
	public void setSpent_point(int spent_point) {
		this.spent_point = spent_point;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getReq_schedule() {
		return req_schedule;
	}
	public void setReq_schedule(String req_schedule) {
		this.req_schedule = req_schedule;
	}
	public String getReq_multiple() {
		return req_multiple;
	}
	public void setReq_multiple(String req_multiple) {
		this.req_multiple = req_multiple;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getReq_msg() {
		return req_msg;
	}
	public void setReq_msg(String req_msg) {
		this.req_msg = req_msg;
	}
	public String getReq_remark() {
		return req_remark;
	}
	public void setReq_remark(String req_remark) {
		this.req_remark = req_remark;
	}
	public String[] getReq_send_number() {
		return req_send_number;
	}
	public void setReq_send_number(String[] req_send_number) {
		this.req_send_number = req_send_number;
	}
	public void setReq_send_number2(String req_send_number) {
		this.req_send_number = null;
		this.req_send_number = new String[1];
		this.req_send_number[0] = req_send_number;
	}
	public String getRes_data() {
		return res_data;
	}
	public void setRes_data(String res_data) {
		this.res_data = res_data;
	}
	public String getRes_response() {
		return res_response;
	}
	public void setRes_response(String res_response) {
		this.res_response = res_response;
	}
	public Smppuser getSmppuser() {
		return smppuser;
	}
	public void setSmppuser(Smppuser smppuser) {
		this.smppuser = smppuser;
	}
	public String getSmppuser_id() {
		return smppuser_id;
	}
	public void setSmppuser_id(String smppuser_id) {
		this.smppuser_id = smppuser_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getSmppuser_com() {
		return smppuser_com;
	}
	public void setSmppuser_com(String smppuser_com) {
		this.smppuser_com = smppuser_com;
	}
	
	
	

}
