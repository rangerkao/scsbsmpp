<%@page import="com.scsb.dao.OrderDAO"%>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
	OrderDAO oDAO = new OrderDAO();
	ArrayList<Order> list = oDAO.getOrder(smppuser2);
	//java.util.Date now = new java.util.Date();
	//String curDateTime = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now);

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
						str = request.getParameter("inout");
						break;
					case 2:
						str = request.getParameter("start_date") + "," + request.getParameter("end_date");
						break;
					case 3:
						str = request.getParameter("search_str");
						break;
					case 4:
						str = request.getParameter("status");
						break;
											
				}
				//out.println("stype:" + request.getParameter("stype") + " str:" + str);
				list = oDAO.getOrder(smppuser2,request.getParameter("stype"),str);
			}
		}
	}
	else
	{
		list = oDAO.getOrder(smppuser2);
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂單管理</title>
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
			document.getElementById('inout_search').style.display="";
			document.getElementById('time_search').style.display="none";
			document.getElementById('number_search').style.display="none";
			document.getElementById('status_search').style.display="none";
			break;
		case '2':
			document.getElementById('inout_search').style.display="none";
			document.getElementById('time_search').style.display="";
			document.getElementById('number_search').style.display="none";
			document.getElementById('status_search').style.display="none";
			break;
		case '3':
			document.getElementById('inout_search').style.display="none";
			document.getElementById('time_search').style.display="none";
			document.getElementById('number_search').style.display="";
			document.getElementById('status_search').style.display="none";
			break;
		case '4':
			document.getElementById('inout_search').style.display="none";
			document.getElementById('time_search').style.display="none";
			document.getElementById('number_search').style.display="none";
			document.getElementById('status_search').style.display="";
			break;
	}
}
</script>
<script type="text/javascript">
        var tableToExcel = (function () {
            var uri = 'data:application/vnd.ms-excel;base64,'
                //, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
				, template = "<?xml version=\"1.0\"?><?mso-application progid=\"Excel.Sheet\"?><Workbook  xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\"><Styles><Style ss:ID=\"Default\" ss:Name=\"Normal\"><Alignment ss:Vertical=\"Bottom\" /><Borders /><Font /><Interior /><NumberFormat /><Protection /></Style></Styles><Worksheet ss:Name=\"{worksheet}\">{table}</Worksheet></Workbook>"
                , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
                , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
            return function (table, name) {
                if (!table.nodeType) table = document.getElementById(table)
                var ctx = { worksheet: name || 'Worksheet', table: parserTable(table) }
                window.location.href = uri + base64(format(template, ctx))
            }
        })()
function parserTable(table)
{
     var rtn="<Table ss:ExpandedColumnCount=\"10\" ss:ExpandedRowCount=\"100\" x:FullColumns=\"1\" x:FullRows=\"1\">\n";
     for (var i = 0, rowsLength = table.rows.length; i < rowsLength; i++) 
     {
    	 rtn +="<Row>\n";
		 var row = table.rows[i];
    	 for (var j = 0, cellsLength = row.cells.length; j < cellsLength-1; j++) 
    	 {
    		 var cell = row.cells[j];
			 rtn += "<Cell>\n";
    		 //var str = cell.innerHTML;
			 //if( str.indexOf("input") > 0 )
			 //{
				//var start = str.indexOf("value")+7;
				//var end = str.lastIndexOf("\"");
				//str = "<Data ss:Type=\"String\">" + str.substr(start,end-start) + "</Data>\n";
			 //}
			 //else
			 //{
			 var str = "<Data ss:Type=\"String\">" + cell.innerHTML + "</Data>\n";			 
			 //}
    		 rtn += str;
    		 rtn += "</Cell>\n";
    	 }
    	 rtn += "</Row>\n";
     }
	 rtn+="</Table>";
     return rtn;
}		
function chk()
{
	var start_date = new Date(document.getElementById('start_date').value);
	var end_date = new Date(document.getElementById('end_date').value);
	if (document.getElementById('start_date').value == '' || document.getElementById('end_date').value == '')
	{
	   alert('請輸入日期欄位');
	   location.href='managerOrder.jsp';
	   return false;
	}
	else if(end_date.getTime() < start_date.getTime())
	{
		alert('結束時間不可比開始時間早');
		location.href='managerOrder.jsp';
		return false;
	}
	else if(start_date.getTime() > end_date.getTime())
	{
		alert('開始時間不可比結束時間晚');
		location.href='managerOrder.jsp';
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
      <div id="content" >
      <!--  <form action="exportlog.jsp" method="post"><input type="submit" name="submit" value="匯出"></form>-->
      <input type="button" onclick="tableToExcel('orderlist', 'W3C Example Table')" value="Export to Excel">
      <form id="search" action="managerOrder.jsp" method="post">
      <input type="hidden" name="action" value="search">
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="1" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("1") ) out.println("checked"); %>>依國內國外<br/>
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="2" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("2") ) out.println("checked"); %>>依時間區間<br/>
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="3" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("3") ) out.println("checked"); %>>依門號<br/>
      <input type="radio" onclick="searchshow(this.value);" name="stype" value="4" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("4") ) out.println("checked"); %>>依狀態<br/>
      <div id="inout_search" style="display: none;"><input name="inout" type="radio" value="0" checked>國內<input name="inout" type="radio" value="1" >國外</div>
      <div id="time_search" style="display: none;">開始日期<input type="text" id="start_date" name="start_date" value="0">結束日期<input type="text" id="end_date" name="end_date" value="0">
      <br/><pre>(Ex:開始日期2014/07/04 00:00:00 結束日期2014/07/04 23:59:59 )</pre></div>
      <div id="number_search" style="display: none;"><input id="search_str" name="search_str" type="text" size="30" maxlength="30"></div>
      <div id="status_search" style="display: none;"><input type="radio" name="status" value="2" checked>成功<input type="radio"  name="status" value="5">失敗<input type="radio"  name="status" value="0">傳送中</div>
      <input type="submit" onclick="return chk()" value="搜尋"><br/>
      </form>
      <a style="font-size:15px; font-family: 微軟正黑體;"><%out.println(" str:" + str); %></a>
      <div id="order"  style="height:600px;overflow:auto;width:900px;">
	
	<% String exportToExcel = request.getParameter("exportToExcel");
 if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) { 
response.setContentType("application/vnd.ms-excel"); 
response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls"); } 
%>
	<table id="orderlist" border="5" cellpadding="8" frame="void" rules="none" width="100%">
		<tr>
			<th>編號</th><th>訂單編號</th><th>花費點數</th><th>發送時間</th><th>訊息內容</th><th width="50">接收號碼</th><th>發送狀態</th><th>備註</th>
		</tr>
		<%
		int order_num=1;
		for(Order o : list){
		%>
			<tr>
				<td><%=order_num %></td>
				<td><%=o.getId() %></td>
				<td><%=o.getSpent_point() %></td>
				<td><%=o.getCreateTime2() %></td>
				<td width=150><%=o.getReq_msg() %></td>
				<td width=10><%=o.getNumber() %></td>
				<td>
				<%if(o.getStatus()==0){
					out.print("傳送中");
				}else if(o.getStatus()==1){
					out.print("傳送中");
				}else if(o.getStatus()==2){
					out.print("已送出");
				}else if(o.getStatus()==3){
					out.print("失敗");
				}else if(o.getStatus()==4){
					out.print("失敗");
				}else if(o.getStatus()==5){
					out.print("失敗");
				}else if(o.getStatus()==6){
					out.print("已送出");
				}else if(o.getStatus()==7){
					out.print("失敗");
				}else if(o.getStatus()==8){
					out.print("失敗");
				}else if(o.getStatus()==9){
					out.print("已送出");
				}else if(o.getStatus()==96){
					out.print("失敗");
				}else if(o.getStatus()==99){
					out.print("失敗");
				}
					%>
				</td>
				<td><%=o.getReq_remark() %></td>
				</tr>
			
		<%
			order_num++;
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