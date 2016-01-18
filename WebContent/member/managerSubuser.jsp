<%@page import="com.scsb.dao.OrderDAO"%>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>子帳號管理</title>
</head>
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<script type="text/javascript" src="../js/modernizr-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>

<script type="text/javascript">
$(function(){
	   
	   $('.detail').click(function(){ 
			location.href = '';
	  });
	});
</script>

<style type="text/css">


</style>
<body>

<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content" >
      <div  style="height:500px; overflow:auto;">
      <form action="managerSubuser.do" method="post">
      <table>
      <tr>
      <td><input type="button" value="新增子帳號"></td><td><input type="button" value="大量新增子帳號"></td><td></td>
      </tr>
      </table>
      </form>     
      <table border="5" cellpadding="8" frame="void" rules="none" width="100%" >
		<tr>
			<th>編號</th><th>電話號碼</th><th>描述</th><th>建立時間</th><th>&nbsp;</th>
		</tr>		
	
      </table>
      
      </div>
      </div>
      </div>
</div>

<%@include file="../foot.jsp" %>

</body>
</html>