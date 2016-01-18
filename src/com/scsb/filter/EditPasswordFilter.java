package com.scsb.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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
import com.scsb.dao.SmppuserDAO;

public class EditPasswordFilter implements Filter{

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
		String ip = req.getRemoteAddr();
		String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String type = req.getParameter("type");
		String action = req.getParameter("action");
		String errMsg ="";
		
		String id;
		String password;
		
		
		//RequestDispatcher requtestDispatcher = req.getRequestDispatcher("/register.jsp");
		//requtestDispatcher.forward(req, resp);

		
		if(type.equals("smppuser")&&action.equals("editPassword")){
			id = request.getParameter("id");
	    	SmppuserDAO smppuserDAO = new SmppuserDAO();
	    	Smppuser smppuser = smppuserDAO.getSmppuser(id);
			password = req.getParameter("password");
			System.out.println(password);
			if(password.equals("") == true ){
				password = smppuser.getPassword();
				System.out.println(password);
			}
			/*else{
				password = req.getParameter("password");
			}*/
			System.out.print(smppuser);
			System.out.print(id);
			
			if(pass && smppuser!=null){
				
				smppuser.setPassword(password);
				
				req.setAttribute("editPassword", smppuser);
				filterChain.doFilter(req, resp);
			}else{
				out.println("<script languate='javascript'>alert('"+errMsg+"');history.go(-1);</script>");
			}
			
		}
	}
	@Override
	public void destroy() {
		
		
	}

	public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
