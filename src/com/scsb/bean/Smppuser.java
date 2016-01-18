package com.scsb.bean;

public class Smppuser {
	private String id;
	private String username;
	private String password;
	private String company;
	private String name;
	private String gender;
	private String email;
	private String address;
	private String postcode;
	private String phone;
	private String mobile;
	private String rid;
	private String fax;
	private String verify_email;
	private String verify_email_code;
	private String verify_mobile;
	private String verify_mobile_code;
	private String status;
	private String create_ip;
	private String create_time;
	private String login_ip;
	private String login_time;
	private String edit_ip;
	private String edit_time;
	private String point;
	private String org_id;
	private Organization organization;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	
	public String getGender2(){
		return this.gender.equals("0")?"先生":"小姐";
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getVerify_email() {
		return verify_email;
	}
	public void setVerify_email(String verify_email) {
		this.verify_email = verify_email;
	}
	public String getVerify_email_code() {
		return verify_email_code;
	}
	public void setVerify_email_code(String verify_email_code) {
		this.verify_email_code = verify_email_code;
	}
	public String getVerify_mobile() {
		return verify_mobile;
	}
	public void setVerify_mobile(String verify_mobile) {
		this.verify_mobile = verify_mobile;
	}
	public String getVerify_mobile_code() {
		return verify_mobile_code;
	}
	public void setVerify_mobile_code(String verify_mobile_code) {
		this.verify_mobile_code = verify_mobile_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_ip() {
		return create_ip;
	}
	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}
	public String getEdit_ip() {
		return edit_ip;
	}
	public void setEdit_ip(String edit_ip) {
		this.edit_ip = edit_ip;
	}
	public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	



	
}
