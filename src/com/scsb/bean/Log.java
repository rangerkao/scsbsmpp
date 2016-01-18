package com.scsb.bean;

public class Log {
	private int log_id;
	private String log_ip;
	private long log_time;
	private String action;
	private Smppuser smppuser;
	
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}
	public long getLog_time() {
		return log_time;
	}
	public void setLog_time(long log_time) {
		this.log_time = log_time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Smppuser getSmppuser() {
		return smppuser;
	}
	public void setSmppuser(Smppuser smppuser) {
		this.smppuser = smppuser;
	}
	
	
	
}
