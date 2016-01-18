package com.scsb.servlet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.scsb.bean.PointRecord;
import com.scsb.dao.PointRecordDAO;


public class AddPointServlet  extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			resp.setContentType("text/html; charset=utf-8"); 
			HttpSession session = req.getSession();
			PrintWriter out = resp.getWriter();	
			PointRecord pointRecord = (PointRecord)req.getAttribute("add_Point");
			PointRecordDAO pdao = new PointRecordDAO();
			
			long index = pdao.add(pointRecord);
			
			if (index != 0 ){
				PointRecord p = pdao.getPointRecord(index);
				
		        out.println("<script languate='javascript'>alert('儲值成功!');location.href='managerPoint.jsp';</script>");
			}else{
				out.println("<script languate='javascript'>alert('儲值失敗!');location.href='managerMember.jsp';</script>");
			}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
