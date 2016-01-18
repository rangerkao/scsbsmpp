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

public class EditPasswordServlet  extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			resp.setContentType("text/html; charset=utf-8"); 
			HttpSession session = req.getSession();
			String type = req.getParameter("type");
			String action = req.getParameter("action");
			PrintWriter out = resp.getWriter();
			
			
			/*------------------------          修改會員個人密碼 -------------------------------------- */
			if (type.equals("smppuser")&&action.equals("editPassword")){
				SmppuserDAO smppuserDAO = new SmppuserDAO();
				Smppuser smppuser = (Smppuser)req.getAttribute("editPassword");
				int i = smppuserDAO.editPassword(smppuser);
				if(i==1){
					session.setAttribute("smppuser", smppuser);
					out.println("<script languate='javascript'>alert('修改成功!');location.href='admin/managerMember.jsp';</script>");
				}else{
					out.println("<script languate='javascript'>alert('修改失敗!');location.href='admin/editPassword.jsp';</script>");
				}
				req.removeAttribute("editPassword");
			}
		}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
