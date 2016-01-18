package com.scsb.servlet.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckValidCodeServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.addHeader("Cache-Control", "no-cache,no-store,must-revalidate,post-check=0,pre-check=0");
		resp.addHeader("Expires", "0");
		resp.addHeader("Pragma", "no-cache"); 
		resp.setContentType("text/xml; charset=utf-8");
		String validCode = req.getSession().getAttribute("validCode").toString().toLowerCase();
		String validcode = req.getParameter("validcode").toLowerCase();
		PrintWriter out = resp.getWriter();
		//System.out.println(validCode+"="+validcode);
		if(validCode.equals(validcode)){
			out.println("1");
		}else{
			out.println("0");
		}
		out.flush();
		out.close();
	}
}
