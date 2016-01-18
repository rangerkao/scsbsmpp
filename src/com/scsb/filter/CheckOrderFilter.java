package com.scsb.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.jws.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Element;

import com.scsb.bean.Smppuser;
import com.scsb.bean.Order;
import com.sun.xml.internal.fastinfoset.util.StringArray;



public class CheckOrderFilter implements Filter{

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
		Boolean pass = true;
		String errMsg ="";
		
		int mode = Integer.parseInt(req.getParameter("mode"));
		int spent_point = 0;
		String req_multiple;
		String req_schedule;
		String[] req_send_number;
		String req_msg;
		String req_remark;
		String uid;
		String smppuser_com;
		
		//RequestDispatcher requtestDispatcher = req.getRequestDispatcher("/register.jsp");
		//requtestDispatcher.forward(req, resp);

		if( mode == 1 ){
			req_send_number = new String[1];
			//spent_point = 1;
			String[] tmp_num = req.getParameter("req_send_number").split("\r\n");;
			req_send_number[0] = tmp_num[0];
			if(req.getParameter("req_msg")==""){
				req_msg = req.getParameter("req_msg1");
			}else{
				req_msg = req.getParameter("req_msg");
			}
			req_remark = req.getParameter("req_remark");
			req_schedule = req.getParameter("req_schedule");
			smppuser_com = req.getParameter("smppuser_com");
			
			if(req_send_number[0].equals("")){
				pass=false;
				errMsg+="Please type number!";
			}
			
			if(req_msg.equals("")){
				pass=false;
				errMsg+="請輸入簡訊內容!";
			}
			
			if(pass){
				Order order = new Order();
				Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
				order.setSmppuser(smppuser);
				order.setMode(mode);
				order.setCreateTime(System.currentTimeMillis());;
				order.setCreateIp(req.getRemoteAddr());
				order.setReq_msg(req_msg);
				order.setReq_send_number(req_send_number);
				order.setReq_multiple("0");
				order.setReq_remark(req_remark);
				order.setReq_schedule(req_schedule);
				order.setSpent_point(spent_point);
				order.setStatus(0);
				order.setSmppuser_com(smppuser_com);
				for(String number : req_send_number){
					if(number.charAt(0)==0){
						order.setArea(0);
						}else{
						order.setArea(1);	
						}
				}
				
				req.setAttribute("create_order", order);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}
		}else if( mode == 2 ){
			req_send_number = null;
			try{
				req_send_number = req.getParameter("req_send_number").split("\r\n");
				System.out.println(req_send_number);
			//	spent_point = req_send_number.length;
			}catch(Exception e){
				errMsg+="Please type number wrong!";
				pass = false;
			}
			//spent_point=3;
			
			if(req.getParameter("req_msg")==""){
				req_msg = req.getParameter("req_msg1");
			}else{
				req_msg = req.getParameter("req_msg");
			}
			req_remark = req.getParameter("req_remark");
			req_schedule = req.getParameter("req_schedule");	
			smppuser_com = req.getParameter("smppuser_com");
			
			if(req_msg.equals("")){
				pass=false;
				errMsg+="請輸入簡訊內容!";
			}
			
			if(pass){
				Order order = new Order();
				Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
				order.setSmppuser(smppuser);
				order.setMode(mode);
				order.setCreateTime(System.currentTimeMillis());;
				order.setCreateIp(req.getRemoteAddr());
				order.setReq_msg(req_msg);
				order.setReq_send_number(req_send_number);
				order.setReq_multiple("0");
				order.setReq_remark(req_remark);
				order.setReq_schedule(req_schedule);
				order.setSpent_point(spent_point);
				order.setStatus(0);
				order.setSmppuser_com(smppuser_com);
					
				req.setAttribute("create_order", order);
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
