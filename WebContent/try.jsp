<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>title</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/head_admin.css" />
</head>
<body>
<div id="wrap">
	<div style="float:right;"><%@include file="login_admin.jsp" %></div>
  <div id="content">
    <div class="left float-l">
      <div id="header">
        <div id="logo">
          <h1> Scsbsmpp 管理頁面</h1>
          <div>manager page</div>
        </div>
        <ul id="nav">
          <li><a href="/scsbsmpp/admin/personal.jsp">管理者資訊</a></li>
          <li><a href="/scsbsmpp/admin/managerMember.jsp">會員管理</a></li>
          <li><a href="/scsbsmpp/authmail.jsp?type=admin">email管理</a></li>
          <li><a href="#">會員簡訊發送清單</a></li>
          <li><a href="#">簡訊發送測試</a></li>
        </ul>
        <div id="footer">
          <div id="ftlink"> <a href="#">Home</a> | <a href="#">About Us</a> | <a href="#">Production</a> | <br/>
            <a href="#">Submission</a> | <a href="#">Contact</a> </div>
          <p id="copyright">© 2008. All Rights Reserved. <br/>
            Designed by <a href="http://www.free-css-templates.com/">Free CSS Templates</a><br/>
            Thanks to <a href="http://www.dubaiapartments.biz/">Dubai Villas</a></p>
        </div>
      </div>
    </div>
    <div class="right folat-r">
      <div id="right-content">
        <div id="top">
          <h2>Free CSS Templates</h2>
          <p>This is sunny Sky, a free, fully standards-compliant CSS template designed by <a href="http://www.free-css-templates.com/">Free CSS Templates</a>.</p>
          <p> This free template is released under a Creative Commons Attributions 2.5 license, so you’re pretty much free to do whatever you want with it (even use it commercially) provided you keep the links in the footer intact. Aside from that, have fun with it :) </p>
          <p>This is sunny Sky, a free, fully standards-compliant CSS template designed by <a href="http://www.free-css-templates.com/">Free CSS Templates</a>.</p>
        </div>
        <img src="images/img.gif" alt="" />
        <div class="section">
          <h2>Sections</h2>
          <ul>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
          </ul>
        </div>
        <div class="section">
          <h2>Sections</h2>
          <ul>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
            <li><a href="#">LINKS LINKS</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- /content -->
  <!-- /footer -->
</div>
</body>
</html>