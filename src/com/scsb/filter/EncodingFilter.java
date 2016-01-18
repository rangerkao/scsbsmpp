package com.scsb.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	private FilterConfig filterConfig;
	private String Encoding;
	private boolean enabled;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Encoding = filterConfig.getInitParameter("Encoding");
		enabled = "true".equalsIgnoreCase(filterConfig.getInitParameter("enabled"))||"1".equalsIgnoreCase(filterConfig.getInitParameter("enabled"));
	}
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		if(Encoding!=null && enabled){
			req.setCharacterEncoding(Encoding);
			resp.setCharacterEncoding(Encoding);
		}
		//System.out.println(((HttpServletRequest)req).getRequestURI());
		filterChain.doFilter(req, resp);
	

	}
	
	@Override
	public void destroy(){
		Encoding = null;
	}
}
