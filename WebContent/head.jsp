<%@page import="com.scsb.bean.Smppuser" %>
<%@page import="com.scsb.dao.SmppuserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>Smpp</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <!-- <link rel="stylesheet" type="text/css" href="css/head.css" /> -->
  <!-- modernizr enables HTML5 elements and feature detects -->
  <!-- <script type="text/javascript" src="js/modernizr-1.5.min.js"></script> -->
</head>
<script type="text/javascript">

</script>
 


<body>
<div style="position:fixed; width:100%;">
	<%
	Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
	if(smppuser==null){

	%>
	<div style="float:right; "><input type="button"  onclick="location.href='login.jsp'" value="登入"></div>
	<% 
	}else{
		SmppuserDAO smppuserDAO = new SmppuserDAO();
		Smppuser upuser = smppuserDAO.getSmppuser(smppuser.getId());
		upuser.setOrganization(smppuser.getOrganization()); 
		session.setAttribute("smppuser",upuser);
		out.println("<div id='head' style='font-size:20px; font-family:微軟正黑體; color:#FFFFFF; float:right;'>");
		out.println(upuser.getUsername());
		out.println(upuser.getName());
		out.println("目前點數為："+upuser.getPoint());
		out.println("<a href='"+request.getContextPath()+"/logout.do?type=smppuser'>登出</a>");
		out.println("</div>");
	}

	%>
  	<header>
      <div id="logo">
	
        <div id="logo_text">
          <!-- class="logo_colour", allows you to change the colour of the text -->
          <h1><img src="/scsbsmpp/images/logo.png" width="70"><a href="/scsbsmpp/index.jsp"><span class="logo_colour">簡訊發送系統</span></a></h1>
          
        </div>
      </div>
      <nav>
        <div id="menu_container">
          <ul class="sf-menu" id="nav">
            <li class="selected"><a href="/scsbsmpp/index.jsp">首頁</a></li>
            <li><a href="/scsbsmpp/member/order.jsp">簡訊發送系統</a></li>
            <li><a href="/scsbsmpp/member/managerOrder.jsp">簡訊結果相關資訊</a></li>
<!--            <li><a href="/scsbsmpp/member/managerSubuser.jsp">子帳號管理</a></li> -->
            <li><a href="#">會員專區</a>
              <ul>
                <li><a href="/scsbsmpp/member/personal.jsp">個人資料修改</a></li>
                <li><a href="/scsbsmpp/member/managerPoint.jsp">儲值記錄</a>
                <li><a href="/scsbsmpp/member/samplesms.jsp">罐頭簡訊</a>
                <li><a href="/scsbsmpp/member/phonebook.jsp">通訊錄</a>
              </ul>
            </li>
            <li><a href="mailto:service@prismscsb.com.tw">聯絡我們</a></li>
          </ul>
        </div>
      </nav>
    </header>
	</div>
	<
</body>
</html>