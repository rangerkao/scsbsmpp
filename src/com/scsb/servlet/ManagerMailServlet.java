package com.scsb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scsb.bean.Admin;
import com.scsb.bean.Smppuser;
import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;
import com.scsb.function.RandomCode;
import com.scsb.function.SendMail;

public class ManagerMailServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = resp.getWriter();
		String type = req.getParameter("type");
		String method = req.getParameter("method");
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String ip = req.getRemoteAddr();
		String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		if(type.equals("smppuser")){
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			Smppuser smppuser = smppuserDAO.login(account, password, ip, time);
			if(smppuser!=null){
				if(method.equals("send")){
					String link = "http://"+req.getLocalAddr()+":"+req.getLocalPort()+req.getContextPath()+"/verifyMail.do?"+
							  "type=smppuser&account="+smppuser.getUsername()+"&verify_email_code="+smppuser.getVerify_email_code();
					SendMail sendMail = new SendMail();
					sendMail.sendMemberVerificationMail(smppuser, link);
					out.println("<script languate='javascript'>alert('email已發送成功!');location.href='"+req.getContextPath()+"/index.jsp';</script>");
				}else if(method.equals("change")){
					if(email!=null){
						smppuser.setEmail(email);
						smppuser.setVerify_email_code(RandomCode.getRandomCode(20));
						smppuser.setVerify_email("0");
						smppuser.setEdit_ip(ip);
						smppuser.setEdit_time(time);
						int i = smppuserDAO.edit(smppuser);
						if(i==1){
							String link = "http://"+req.getLocalAddr()+":"+req.getLocalPort()+req.getContextPath()+"/verifyMail.do?"+
									  "type=smppuser&account="+smppuser.getUsername()+"&verify_email_code="+smppuser.getVerify_email_code();
							SendMail sendMail = new SendMail();
							sendMail.sendMemberVerificationMail(smppuser, link);
							out.println("<script languate='javascript'>alert('更改成功，請至信箱收取認證信!');history.go(-1);</script>");
						}else{
							out.println("<script languate='javascript'>alert('更改失敗!');history.go(-1);</script>");
						}
					}

				}
			}else{
				out.println("<script languate='javascript'>alert('帳號密碼有誤!');history.go(-1);</script>");
			}

			
		}
		

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
