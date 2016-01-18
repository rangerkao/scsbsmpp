package com.scsb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;

public class VerifyMailServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = resp.getWriter();
		String type = req.getParameter("type");
		String account = req.getParameter("account");
		String verify_email_code = req.getParameter("verify_email_code");
		String ip = req.getRemoteAddr();
		String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			
		
		if(type.equals("smppuser")){
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			if(smppuserDAO.verifyMail(account, verify_email_code, ip ,time)){ 
				out.println("<script languate='javascript'>alert('email認證成功！');location.href='"+req.getContextPath()+"/index.jsp';</script>");
			}else{
				out.println("<script languate='javascript'>alert('email認證失敗！');location.href='"+req.getContextPath()+"/authmail.jsp?type=smppuser&action=failed'</script>");
			}
		}

		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
