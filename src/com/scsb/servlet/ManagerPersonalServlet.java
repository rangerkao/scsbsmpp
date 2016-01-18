package com.scsb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scsb.bean.Admin;
import com.scsb.bean.Smppuser;
import com.scsb.dao.AdminDAO;
import com.scsb.dao.SmppuserDAO;
import com.scsb.function.SendMail;

public class ManagerPersonalServlet  extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			resp.setContentType("text/html; charset=utf-8"); 
			HttpSession session = req.getSession();
			String type = req.getParameter("type");
			String action = req.getParameter("action");
			PrintWriter out = resp.getWriter();
			
			/*------------------------          會員註冊 -------------------------------------- */
			if (type.equals("smppuser")&&action.equals("reg")){
				SmppuserDAO smppuserDAO = new SmppuserDAO();
				Smppuser smppuser = (Smppuser)req.getAttribute("reg");
				int i = smppuserDAO.save(smppuser);
				if(i==1){
					resp.sendRedirect(req.getContextPath()+"/index_admin.jsp?type=smppuser&action=reg");
				}else{
					resp.sendRedirect("index_admin.jsp");
				}
				req.removeAttribute("reg");
			}
			
			/*------------------------          管理員註冊 -------------------------------------- */
			if(type.equals("admin")&&action.equals("reg")){
				AdminDAO adminDAO = new AdminDAO();
				Admin admin = (Admin)req.getAttribute("reg");
				int i = adminDAO.save(admin);
				if(i==1){
					resp.sendRedirect(req.getContextPath()+"/index_admin.jsp?type=admin&action=reg");
				}else{
					resp.sendRedirect("index_admin.jsp");
				}
				req.removeAttribute("reg");
			}

			/*------------------------          會員修改個人資料 -------------------------------------- */
			if (type.equals("smppuser")&&action.equals("edit")){
				SmppuserDAO smppuserDAO = new SmppuserDAO();
				Smppuser smppuser = (Smppuser)req.getAttribute("edit");
				int i = smppuserDAO.edit(smppuser);
				if(i==1){
					session.setAttribute("smppuser", smppuser);
					out.println("<script languate='javascript'>alert('修改成功!');location.href='member/personal.jsp';</script>");
				}else{
					out.println("<script languate='javascript'>alert('修改失敗!');location.href='member/personal.jsp';</script>");
				}
				req.removeAttribute("edit");
			}
			/*------------------------          管理者修改個人資料 -------------------------------------- */
			if(type.equals("admin")&&action.equals("edit")){
				AdminDAO adminDAO = new AdminDAO();
				Admin admin = (Admin)req.getAttribute("edit");
				int i = adminDAO.edit(admin);
				if(i==1){
					session.setAttribute("admin", admin);
					out.println("<script languate='javascript'>alert('修改成功!');location.href='admin/personal.jsp';</script>");			
				}else{
					out.println("<script languate='javascript'>alert('修改失敗!');location.href='admin/personal.jsp';</script>");
				}
				req.removeAttribute("edit");
			}

			
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
