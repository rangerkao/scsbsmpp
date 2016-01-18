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
<link rel="stylesheet" type="text/css" href="../css/reset.css" />
<link rel="stylesheet" type="text/css" href="../css/head_admin.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂單明細</title>
</head>
<body>

<div id="wrap">
	<div style="float:right;"><%@include file="../login_admin.jsp" %></div>
	<div id="content">
  	<%@include file="../head_admin-left.jsp" %>
  	<div class="right folat-r">
      <div id="right-content">
        <div id="top">
        </div>
        
        <div class="section">
          <h2></h2>
          <div id="bg" style="overflow:auto; color:#000000">
	<table border="5" cellpadding="8" frame="void" rules="none" width="100%">
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
    </div>
  	</div>
  </div>
</body>
</html>