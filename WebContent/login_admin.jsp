<%@page import="com.scsb.bean.Admin"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Admin admin = (Admin)session.getAttribute("admin");
	if(admin==null){
			
%>
		<form action="login.do" method="post">
			<input type="hidden" name="type" value="admin"/>
			<input type="text" name="account" value="Admin"/>
			<input type="password" name="password" value="admin"/>
			<input type="submit" value="登入"/>
			<!--  <input type="button" value="註冊" onclick="location.href='register_admin.jsp'">-->
		</form>
<% 
	}else{
		out.println(admin.getAccount());
		out.println(admin.getName());
		out.println("<a href='"+request.getContextPath()+"/logout.do?type=admin'>登出</a>");
	}

%>