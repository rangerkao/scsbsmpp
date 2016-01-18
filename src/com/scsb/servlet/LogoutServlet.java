package com.scsb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String type = req.getParameter("type");
		if (type.equals("smppuser")){
			req.getSession().removeAttribute("smppuser");
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}else if (type.equals("admin")){
			req.getSession().removeAttribute("admin");
			resp.sendRedirect(req.getContextPath()+"/index_admin.jsp");
		}

	}
	
}
