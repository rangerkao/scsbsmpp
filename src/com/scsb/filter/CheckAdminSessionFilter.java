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

import com.scsb.bean.Admin;


public class CheckAdminSessionFilter implements Filter {
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
		resp.setContentType("text/html; charset=utf-8");
		Admin admin = (Admin)session.getAttribute("admin");
		if(admin!=null){
			filterChain.doFilter(req, resp);
		}else{
			resp.getWriter().write("<script>alert('抱歉！您尚未登入！');location.href='"+request.getContextPath()+"/index_admin.jsp'</script>");
		}
	

	}
	
	@Override
	public void destroy() {
		
		
	}
}
