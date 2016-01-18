package com.scsb.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scsb.bean.Admin;
import com.scsb.bean.Smppuser;
import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;
import com.scsb.function.RandomCode;

public class ManagerPersonalFilter implements Filter{

	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		//System.out.println(this.filterConfig.getFilterName());
	}
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		resp.setContentType("text/html; charset=utf-8"); 
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		PrintWriter out = resp.getWriter();
		HttpSession session = request.getSession();
		Boolean pass = true;
		String ip = req.getRemoteAddr();
		String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String type = req.getParameter("type");
		String action = req.getParameter("action");
		String errMsg ="";
		
		String id;
		String username;
		String account;
		String name;
		String password;
		String company;
		String gender;
		String email;
		String country;
		String area;
		String address;
		String postcode;
		String phone;
		String mobile;
		String verify_email;
		String verify_email_code;
		String verify_mobile;
		String verify_mobile_code;
		String create_ip;
		String create_time;
		String login_ip;
		String login_time;
		String edit_ip;
		String edit_time;
		String status;
		String fax;
		String rid;
		String point;
		String org_id;
		
		//RequestDispatcher requtestDispatcher = req.getRequestDispatcher("/register.jsp");
		//requtestDispatcher.forward(req, resp);

		if(type.equals("smppuser")&&action.equals("reg")){
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			username = req.getParameter("username");
			password =req.getParameter("password");
			company = req.getParameter("company");
			name = req.getParameter("name");
			gender = req.getParameter("gender");
			email = req.getParameter("email");
			country = req.getParameter("country");
			area = req.getParameter("area");
			address = country+"-"+area+"-"+req.getParameter("address");
			phone = req.getParameter("phone");
			mobile = req.getParameter("mobile");
			fax = req.getParameter("fax");
			rid = req.getParameter("rid");
			point = "0";
			verify_email = "1";
			verify_email_code = RandomCode.getRandomCode(20);
			verify_mobile = "0";
			verify_mobile_code = RandomCode.getRandomCode(8);
			status = "1";
			create_ip = ip;
			create_time = time;
			login_ip = ip;
			login_time = time;
			edit_ip = ip;
			edit_time = time;
			org_id = "1";
			
			postcode = req.getParameter("postcode");
			if(smppuserDAO.findSmppuser(username)){
				pass=false;
				errMsg+="帳號已有人註冊\\n";
			}
			
			if(!username.matches("^[A-Za-z0-9_-]{6,20}$")){
				pass=false;
				errMsg+="帳號必須以英文或數字組成長度6~20\\n";
			}
			
			if(pass){
				Smppuser smppuser = new Smppuser();
				
				smppuser.setUsername(username);
				smppuser.setPassword(password);
				smppuser.setCompany(company);
				smppuser.setName(name);
				smppuser.setGender(gender);
				smppuser.setEmail(email);
				smppuser.setAddress(address);
				smppuser.setPhone(phone);
				smppuser.setMobile(mobile);
				smppuser.setFax(fax);
				smppuser.setRid(rid);
				smppuser.setVerify_email(verify_email);
				smppuser.setVerify_email_code(verify_email_code);
				smppuser.setVerify_mobile(verify_mobile);
				smppuser.setVerify_mobile_code(verify_mobile_code);
				smppuser.setStatus(status);
				smppuser.setCreate_ip(create_ip);
				smppuser.setCreate_time(create_time);
				smppuser.setLogin_ip(login_ip);
				smppuser.setLogin_time(login_time);
				smppuser.setEdit_ip(edit_ip);
				smppuser.setEdit_time(edit_time);			
				smppuser.setPostcode(postcode);
				smppuser.setPoint(point);
				smppuser.setOrg_id(org_id);
				req.setAttribute("reg", smppuser);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}
			
		}
		
		if(type.equals("admin")&&action.equals("reg")){
			AdminDAO adminDAO = new AdminDAO();
			account = req.getParameter("account");
			password =req.getParameter("password");
			name = req.getParameter("name");
			
			if(adminDAO.findAdmin(account)){
				pass=false;
				errMsg+="帳號已有人註冊\\n";
			}
			
			if(!account.matches("^[a-z0-9_-]{6,20}$")){
				pass=false;
				errMsg+="帳號必須以英文或數字組成長度6~20\\n";
			}
			
			
			if(pass){
				Admin admin = new Admin();
				
				admin.setAccount(account);
				admin.setPassword(password);
				admin.setName(name);
				req.setAttribute("reg", admin);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}			
		}
		
		if(type.equals("smppuser")&&action.equals("edit")){
			Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			id = smppuser.getId();
			password = req.getParameter("password");
			System.out.println(password);
			if(password.equals("") == true ){
				password = smppuser.getPassword();
				System.out.println(password);
			}
			/*else{
				password = req.getParameter("password");
			}*/
			company = req.getParameter("company");
			name = req.getParameter("name");
			gender = req.getParameter("gender");
			email = req.getParameter("email");
			country = req.getParameter("country");
			area = req.getParameter("area");
			address = country+"-"+area+"-"+req.getParameter("address");
			phone = req.getParameter("phone");
			mobile = req.getParameter("mobile");
			fax = req.getParameter("fax");
			rid = req.getParameter("rid");
			verify_email = "1";
			verify_email_code = (smppuser.getEmail().equals(email)&&smppuser.getVerify_email().equals("1"))?smppuser.getVerify_email_code():RandomCode.getRandomCode(20);
			verify_mobile = (smppuser.getPhone().equals(phone)&&smppuser.getVerify_mobile().equals("1"))?"1":"0";
			verify_mobile_code = (smppuser.getPhone().equals(phone)&&smppuser.getVerify_mobile().equals("1"))?smppuser.getVerify_mobile_code():RandomCode.getRandomCode(8);
			edit_ip = ip;
			edit_time = time;
			status = (req.getParameter("benefit")==null)?"1":"2";
			postcode = req.getParameter("postcode");

			
			if(pass && smppuser!=null){
				if(smppuser.getEmail().equals(email)){
					req.setAttribute("editMail", false);
				}else{
					req.setAttribute("editMail", true);
				}
			
				smppuser.setPassword(password);
				smppuser.setCompany(company);
				smppuser.setName(name);
				smppuser.setGender(gender);
				smppuser.setEmail(email);
				smppuser.setAddress(address);
				smppuser.setPhone(phone);
				smppuser.setMobile(mobile);
				smppuser.setFax(fax);
				smppuser.setRid(rid);
				smppuser.setVerify_email(verify_email);
				smppuser.setVerify_email_code(verify_email_code);
				smppuser.setVerify_mobile(verify_mobile);
				smppuser.setVerify_mobile_code(verify_mobile_code);
				smppuser.setEdit_ip(edit_ip);
				smppuser.setEdit_time(edit_time);			
				smppuser.setPostcode(postcode);
				smppuser.setStatus(status);
				req.setAttribute("edit", smppuser);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}
			
		}
		
		if(type.equals("admin")&&action.equals("edit")){
			Admin admin = (Admin)session.getAttribute("admin");
			AdminDAO adminDAO = new AdminDAO();
			id = admin.getId();
			password =req.getParameter("password");
			name = req.getParameter("name");
			
			//System.out.println(admin);
			
			if(pass && admin!=null){
				
				admin.setPassword(password);
				admin.setName(name);
				
				req.setAttribute("edit", admin);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}			
		}

	}
	
	@Override
	public void destroy() {
		
		
	}

	public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
