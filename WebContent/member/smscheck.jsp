<%@page import="com.scsb.bean.Smppuser"%>  
<%@page import="com.scsb.bean.PhoneBook"%>
<%@page import="com.scsb.bean.Phone"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
try
{
	Connection conn = DriverManager.getConnection("jdbc:postgresql://106.186.120.118:5432/smppdb?user=smpper&password=SmIpp3r&autoReconnect=true");
}
catch(Exception e)
{
	out.println("DB connect error!");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/head.css" />
  <!-- modernizr enables HTML5 elements and feature detects -->
  <script type="text/javascript" src="../js/modernizr-1.5.min.js"></script>
  <script type="text/javascript" src="../js/jquery.js"></script>
  <script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
<title>簡訊查詢系統</title>
</head>

<body>
<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
		<div id="content">
		</div>
</div>
</div>
<%@include file="../foot.jsp" %>   
</body>
</html>