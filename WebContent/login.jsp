<%@page import="com.scsb.bean.Smppuser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
<%
	Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
	if(smppuser==null){

%>
		<form action="login.do" method="post">
			<input type="hidden" name="type" value="smppuser"/>
			<div style="background-image:url(images/login.png); height:100px; width:250px; margin-top: 18%; margin-left: 40%;">
			<div style="top: 30%;">
			帳號：<input type="text" name="account" value=""/><br/>
			密碼：<input type="password" name="password" value=""/><br/>
			</div>
			<div align="right">
			<input type="submit" value="登入" />
			
			</div>
			</div>
		</form>
		
<% 
	}else{
		out.println("<div style='font-size:20px; font-family:微軟正黑體; color:#FFFFFF;'>");
		out.println(smppuser.getUsername());
		out.println(smppuser.getName());
		out.println("剩餘點數："+smppuser.getPoint());
		out.println("<a href='"+request.getContextPath()+"/logout.do?type=smppuser'>登出</a>");
		out.println("</div>");
		out.println("<meta http-equiv='refresh' content='0;url=/scsbsmpp/index.jsp' />");
	}

%>