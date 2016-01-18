package com.scsb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scsb.bean.Admin;
import com.scsb.bean.Smppuser;
import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html; charset=utf-8");
		String type = req.getParameter("type");
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		
		
		if(type.equals("smppuser")){
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			String ip = req.getRemoteAddr();
			String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			Smppuser smppuser = smppuserDAO.login(account,password,ip,time);
			
			if(smppuser!=null){
				HttpSession session = req.getSession();
				session.setAttribute("smppuser", smppuser);
				resp.sendRedirect(req.getContextPath()+"/index.jsp");
			}else{
				
				resp.getWriter().write("<script>alert('帳號或密碼輸入錯誤！');location.href='"+req.getContextPath()+"/login.jsp'</script>");
			}
		}else if(type.equals("admin")){
			AdminDAO adminDAO = new AdminDAO();
			String ip = req.getRemoteAddr();
			String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			Admin admin = adminDAO.login(account,password,ip,time);
			
			if(admin!=null){
				HttpSession session = req.getSession();
				session.setAttribute("admin", admin);
				resp.sendRedirect(req.getContextPath()+"/index_admin.jsp");
			}else{
				resp.sendRedirect(req.getContextPath()+"/index_admin.jsp");
			}			
		}

		
	}
	
}
