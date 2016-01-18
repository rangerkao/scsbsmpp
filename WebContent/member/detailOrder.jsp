<%@page import="com.scsb.dao.OrderDetailDAO"%>
<%@page import="com.scsb.dao.OrderDAO"%>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.OrderDetail"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	long id = Integer.parseInt(request.getParameter("id"));
	OrderDAO oDAO = new OrderDAO();
	OrderDetailDAO odDAO = new OrderDetailDAO();
	Order o =  oDAO.getOrder(id);
	ArrayList<OrderDetail> list = odDAO.getOrderDetail(o.getId());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<script type="text/javascript" src="../js/modernizr-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂單明細</title>
</head>
<body>

<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <%@include file="../news.jsp" %>
      <div id="content">
	<table>
		<tr>
			<th>發送編號</th>
			<th>發送號碼</th>
			<th>訂單編號</th>
		</tr>

		<%
		for(OrderDetail od : list){
		%>
			<tr>
				<td><%=od.getId() %></td>
				<td><%=od.getReq_send_number() %></td>
				<td><%=od.getOrder_id() %>
			</tr>
		<% 
		}
		%>
	</table>
	<input name="Submit" type="button" id="Submit" onClick="javascript:history.back(1)" value="回一上頁" />
</div>
    </div>
</div>
<%@include file="../foot.jsp" %>
</body>
</html>