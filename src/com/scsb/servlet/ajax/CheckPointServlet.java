package com.scsb.servlet.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;

public class CheckPointServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.addHeader("Cache-Control", "no-cache,no-store,must-revalidate,post-check=0,pre-check=0");
		resp.addHeader("Expires", "0");
		resp.addHeader("Pragma", "no-cache"); 
		resp.setContentType("text/xml; charset=utf-8"); 
		PrintWriter out = resp.getWriter();
		String type = req.getParameter("type");
		String point = req.getParameter("point");
		String username = req.getParameter("username");
		if(type.equals("smppuser") && type.equals("point")){
			SmppuserDAO smppuserDAO = new SmppuserDAO();
			if(smppuserDAO.findPoint(username , point)){
				out.println(point);
			}else{
				out.println("0");
			}
		
		}

		out.flush();
		out.close();
	}

}
