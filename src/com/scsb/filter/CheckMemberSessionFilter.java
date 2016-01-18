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

public class CheckMemberSessionFilter implements Filter {
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		//System.out.println(this.filterConfig.getFilterName());
	}
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
		resp.setContentType("text/html; charset=utf-8");
		
		
		if(smppuser!=null){
			if(smppuser.getVerify_email().equals("0")){
				response.sendRedirect(request.getContextPath()+"/authmail.jsp?type=smppuser&action=verify");
			//}else if(smppuser.getPoint().equals("0")){
			//	resp.getWriter().write("<script>alert('抱歉！您目前點數不足！');location.href='"+request.getContextPath()+"/index.jsp'</script>");
			}else if(smppuser.getVerify_email().equals("1")){
				filterChain.doFilter(req, resp);
			}
		}else{
			resp.getWriter().write("<script>alert('抱歉！您尚未登入！');location.href='"+request.getContextPath()+"/login.jsp'</script>");
		}
	

	}
	
	@Override
	public void destroy() {
		
		
	}
}
