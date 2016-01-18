<%@page import="com.scsb.dao.PointRecordDAO" %>
<%@page import="com.scsb.bean.PointRecord"%>
<%@page import="com.scsb.dao.SmppuserDAO"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="com.scsb.dao.AdminDAO"%>
<%@page import="com.scsb.bean.Admin"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser");
	PointRecordDAO pointDAO = new PointRecordDAO();
	ArrayList<PointRecord> list = pointDAO.getPointRecord(smppuser2);

	String action = request.getParameter("action");
	String str = new String();

	if( action != null && action.length() > 0 )
	{
		if( action.equals("search") )
		{
			
			if((request.getParameter("stype") != null ))
			{
				
				int type = Integer.valueOf(request.getParameter("stype"));
				switch(type)
				{
					case 1:
						str = request.getParameter("uid");
						break;
					case 2:
						str = request.getParameter("start_date") + "," + request.getParameter("end_date");
						break;
				}
				
				out.println("stype:" + request.getParameter("stype") + " str:" + str);
				list = pointDAO.getPointRecord(smppuser2,request.getParameter("stype"),str);
			}
		}
	}
	else
	{
		list = pointDAO.getPointRecord(smppuser2);
	}
    %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>儲值記錄</title>
</head>
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<link href="../css/jquery-ui.css" rel="stylesheet">
<!--  <script type="text/javascript" src="../js/modernizr-1.5.min.js"></script>  
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
-->
  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
  <link href="../css/jquery-ui-timepicker-addon.css" rel='stylesheet'>
  <script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
  <script type='text/javascript' src='../js/jquery-ui-sliderAccess.js'></script>
  <link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
<script language="Javascript">
function searchshow(type)
{
	switch(type)
	{
		case '1':
			document.getElementById('uid_search').style.display="";
			document.getElementById('time_search').style.display="none";
			break;
		case '2':
			document.getElementById('uid_search').style.display="none";
			document.getElementById('time_search').style.display="";
			break;
	}
}
function chk()
{
	var start_date = new Date(document.getElementById('start_date').value);
	var end_date = new Date(document.getElementById('end_date').value);
	if (document.getElementById('start_date').value == '' || document.getElementById('end_date').value == '')
	{
	   alert('請輸入日期欄位');
	   location.href='managerPoint.jsp';
	   return false;
	}
	else if(end_date.getTime() < start_date.getTime())
	{
		alert('結束時間不可比開始時間早');
		location.href='managerPoint.jsp';
		return false;
	}
	else if(start_date.getTime() > end_date.getTime())
	{
		alert('開始時間不可比結束時間晚');
		location.href='managerPoint.jsp';
		return false;
	}
	else
	{
		return true;
	}
}
</script>
<style type="text/css">


</style>
<body>
<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content">
      <div  style="height:600px;overflow:auto; margin-top: 5%; width:800px;">
      <%@page import="java.text.SimpleDateFormat"%>
      <form id="search" action="managerPoint.jsp" method="post">
      <input type="hidden" name="action" value="search">
      <!--  <input type="radio" onclick="searchshow(this.value);" name="stype" value="1" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("1") ) out.println("checked"); %>>依會員編號<br/>-->
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="2" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("2") ) out.println("checked"); %>>依時間區間<br/>
      <div id="uid_search" style="display: none;"><input name="uid" type="text" value="0" checked></div>
      <div id="time_search" style="display: none;">開始日期<input type="text" id="start_date" name="start_date" value="0">結束日期<input type="text" id="end_date" name="end_date" value="0">
      <br/><pre>(Ex:開始日期2014/07/04 00:00:00 結束日期2014/07/04 23:59:59 )</pre></div>
      <input type="submit" onclick="return chk()" value="搜尋"><br/>
      </form>
      <%
	out.println("<a>"+"stype:" + request.getParameter("stype") + " str:" + str+"</a>");
	%>
	<table border="5" cellpadding="8" frame="void" rules="none" style="width:100%;">
		<tr>
			<th>編號</th><th>儲值編號</th><th>加值點數</th><th>加值費用</th><th>加值時間</th><th>交易說明</th><th>會員編號</th>
		</tr>
		<%
		int point_num=1;
		for(PointRecord p : list){
		%>
			<tr>
				<td><%=point_num %></td>
				<td><%=p.getId() %></td>
				<td><%=p.getAddAmount() %></td>
				<td><%=p.getAddPrice() %></td>
				<td><%=p.getAddTime2() %></td>
				<td><%=p.getAdd_remark() %>
				<td><%=p.getSmppuser_id() %></td>
			</tr>
		<%
		point_num++;
		}
		%>
	</table>
	</div>
</div>
    </div>
</div>
<script>
$(document).ready(function(){
var opt={dateFormat: 'yy/mm/dd',
		 showSecond: true,
		 timeFormat: 'HH:mm:ss'
		 };
$('#start_date').datetimepicker(opt);
$('#start_date').datetimepicker("setDate", <% if( request.getParameter("start_date") == null ) { out.print("new Date()"); } else { out.print("\"" + request.getParameter("start_date") + "\"" ); }%>);
$('#start_date').datetimepicker().on("blur", function (e) {
    var curDate = $(this).val();
    try {
    	var r = $.datepicker.parseDateTime("yy/mm/dd","HH:mm:ss",curDate);
    } catch(err) {
        alert('Not VALID!');
        $(this).focus();
    }
});
$('#end_date').datetimepicker(opt);
$('#end_date').datetimepicker("setDate", <% if( request.getParameter("end_date") == null ) { out.print("new Date()"); } else { out.print("\"" + request.getParameter("end_date") + "\"" ); }%>);
$('#end_date').datetimepicker().on("blur", function (e) {
    var curDate = $(this).val();
    try {
    	var r = $.datepicker.parseDateTime("yy/mm/dd","HH:mm:ss",curDate);
    } catch(e) {
        alert('Not VALID!');
        $(this).focus();
    }
});
});
searchshow(<%=request.getParameter("stype") %>);
</script>
<%@include file="../foot.jsp" %>
</body>
</html>