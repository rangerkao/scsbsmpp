package com.scsb.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scsb.bean.Smppuser;
import com.scsb.bean.PointRecord;;



public class CheckPointFilter implements Filter{

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
		Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
		Boolean pass = true;
		String errMsg ="";
		String action = req.getParameter("action");
		
		int id;
		int add_amount;
		int add_price;
		String add_remark;
		String smppuser_com;
		
		//RequestDispatcher requtestDispatcher = req.getRequestDispatcher("/register.jsp");
		//requtestDispatcher.forward(req, resp);
		if(action.equals("add")){
			id = Integer.parseInt(req.getParameter("id"));
			add_amount = Integer.parseInt(req.getParameter("add_amount"));
			add_price = Integer.parseInt(req.getParameter("add_price"));
			add_remark = req.getParameter("add_remark");
			smppuser_com = req.getParameter("smppuser_com");
			
			if(pass){
				PointRecord pointRecord = new PointRecord();
				pointRecord.setAddAmount(add_amount);
				pointRecord.setAddPrice(add_price);
				pointRecord.setAddTime(System.currentTimeMillis());
				pointRecord.setAdd_remark(add_remark);
				pointRecord.setSmppuser_com(smppuser_com);
				pointRecord.setSmppuser_id(id);
				
				req.setAttribute("add_Point", pointRecord);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}
		}
	}
	
	@Override
	public void destroy() {
		
		
	}

	
}
