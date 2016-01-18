<%@page import="com.scsb.dao.OrderDAO" %>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="com.scsb.bean.Phone"%>
<%@page import="com.scsb.bean.PhoneBook"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
	PhoneBook pbook = new PhoneBook();
	ArrayList<Phone> list = new ArrayList<Phone>();
	String action = request.getParameter("action");

	if( action != null && action.length() > 0 )
	{
		if(action.equals("edit")  )
		{
			Phone ephone = new Phone();			
			if(( request.getParameter("uid") != null ) && ( request.getParameter("phone_id") != null ) )
			{		
				ephone.setID(Integer.valueOf(request.getParameter("phone_id")));
				ephone.setNumber(request.getParameter("number"));
				ephone.setDescription(request.getParameter("description"));
				ephone.setGroup(request.getParameter("group"));
				pbook.editPhone(ephone);
			}
			else
			{
				throw new Exception("UID or Phone ID is missing"); 
			}
			list = pbook.getPhones(smppuser2);
		}
		else if(action.equals("add") )
		{
			Phone ephone = new Phone();			
			if(( request.getParameter("uid") != null ) && ( request.getParameter("number") != null ) && ( request.getParameter("description") != null ))
			{		
				ephone.setNumber(request.getParameter("number"));
				ephone.setDescription(request.getParameter("description"));
				ephone.setGroup(request.getParameter("group"));
				pbook.addPhone(request.getParameter("uid"),ephone);				
			}
			list = pbook.getPhones(smppuser2);
		}
		else if(action.equals("delete"))
		{
			if(( request.getParameter("uid") != null ) && ( request.getParameter("phone_id") != null ) )
			{
				pbook.deletePhone(request.getParameter("uid"),request.getParameter("phone_id"));
			}
			list = pbook.getPhones(smppuser2);
		}
		else if( action.equals("search") )
		{
			if((request.getParameter("stype") != null ) && ( request.getParameter("search_str") != null))
			{
				out.println("stype:" + request.getParameter("stype") + " str:" + request.getParameter("search_str"));
				list = pbook.getPhones(smppuser2,request.getParameter("stype"),request.getParameter("search_str"));
			}
		}
	}
	else
	{
		list = pbook.getPhones(smppuser2);
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通訊錄管理</title>
</head>
<link rel="stylesheet" type="text/css" href="../css/head.css" />
<script type="text/javascript" src="../js/modernizr-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-twzipcode-1.4.1.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">

<script type="text/javascript">
$(function(){
	   
	   $('.detail').click(function(){ 
			location.href = '';
	  });
	});
</script>

<script language="JavaScript"> 
var tableToExcel = (function() {
	  var uri = 'data:application/vnd.ms-excel;base64,'
	    //, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
		, template = "<?xml version=\"1.0\"?><?mso-application progid=\"Excel.Sheet\"?><Workbook  xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\"><Styles><Style ss:ID=\"Default\" ss:Name=\"Normal\"><Alignment ss:Vertical=\"Bottom\" /><Borders /><Font /><Interior /><NumberFormat /><Protection /></Style></Styles><Worksheet ss:Name=\"{worksheet}\">{table}</Worksheet></Workbook>"
	    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
	    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	  return function(table, name) {
	    if (!table.nodeType) table = document.getElementById(table)
	    var ctx = {worksheet: name || 'Worksheet', table: parserTable(table)}
	    window.location.href = uri + base64(format(template, ctx))
	  }
	})()
function parserTable(table)
{
     var rtn="<Table ss:ExpandedColumnCount=\"10\" ss:ExpandedRowCount=\"100\" x:FullColumns=\"1\" x:FullRows=\"1\">\n";
     for (var i = 0, rowsLength = table.rows.length; i < rowsLength-1; i++) 
     {
    	 rtn +="<Row>\n";
		 var row = table.rows[i];
    	 for (var j = 0, cellsLength = row.cells.length; j < cellsLength-1; j++) 
    	 {
    		 var cell = row.cells[j];
			 rtn += "<Cell>\n";
    		 var str = cell.innerHTML;
			 if( str.indexOf("input") > 0 )
			 {
				var start = str.indexOf("value")+7;
				var end = str.lastIndexOf("\"");
				str = "<Data ss:Type=\"String\">" + str.substr(start,end-start) + "</Data>\n";
			 }
			 else
			 {
			    str = "<Data ss:Type=\"String\">" + cell.innerHTML + "</Data>\n";			 
			 }
    		 rtn += str;
    		 rtn += "</Cell>\n";
    	 }
    	 rtn += "</Row>\n";
     }
	 rtn+="</Table>";
     return rtn;
}		
</script> 

<style type="text/css">


</style>
<body>

<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content" >
      <a href=upload_sample.csv>通訊錄上傳範例</a>
      <input type="button" onclick="window.open('upload.jsp','上傳通訊錄',config='height=500px,width=500px')" value="上傳通訊錄">
      <input type="button" onclick="tableToExcel('phone', 'W3C Example Table')" value="Export to Excel">
      <form id="search" action="phonebook.jsp" method="post">
      <input id="search_str" name="search_str" type="text" size="30" maxlength="30">
      <input type="hidden" name="action" value="search">
      <input type="submit" value="搜尋">
      <input type="radio" name="stype" value="1" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("1") ) { out.println("checked"); } else { out.println("checked"); } %>>依號碼
      <input type="radio" name="stype" value="2" <% if ( request.getParameter("stype") != null && request.getParameter("stype").equals("2") ) out.println("checked"); %>>依群組
      </form>
      <%
	out.println("<a>"+"stype:" + request.getParameter("stype") + " str:" +request.getParameter("search_str") +"</a>");
	%>
      <div  style="height:600px; overflow:auto; width:900px;">     
      <table border="5" cellpadding="8" frame="void" rules="none" width="100%" id="phone">
		<tr>
			<th>編號</th><th>電話號碼</th><th>描述</th><th>建立時間</th><th>群組</th><th>&nbsp;</th>
		</tr>		
		<%
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		int phone_num=1;
		for( Phone phones : list)
		{

		%>
			<form id="form" action="phonebook.jsp" method="post"> 
			<tr>
			<td><%=phone_num %></td>
			<td><input type="text" name="number" maxlength="20" value="<%=phones.getNumber()%>"></td>
			<td><input type="text" name="description" maxlength="200" value="<%=phones.getDescription()%>"></td>
			<td><%=phones.getCreatTime() %></td>
			<td><input type="text" name="group" maxlength="20" value="<%=phones.getGroup() %>"></td>
			<td><input type="hidden" name="uid" value="<%=smppuser2.getId()%>">
			    <input type="hidden" name="phone_id" value="<%=phones.getID()%>">
			    <input type="hidden" id="action_<%=phones.getID() %>" name="action" value="">
			    <input type="submit" value="修改" onclick='document.getElementById("action_<%=phones.getID() %>").value="edit";'>
			    <input type="submit" value="刪除" onclick='document.getElementById("action_<%=phones.getID() %>").value="delete";'>
			</td>
			</tr>
			</form>
		<%
			phone_num++;	
		}
		%>
		<tr>
			<form action="phonebook.jsp" method="post"> 
			<th>&nbsp;</th>
			<th><input type="text" name="number" maxlength="20"></th>
			<th><input type="text" name="description" maxlength="200"></th>
			<th></th>
			<th><input type="text" name="group" maxlength="20" value="<%if( request.getParameter("group") != null ){ out.println(request.getParameter("group")); } else {out.println("無群組");} %>"></th>
			<th>
				<input type="submit" value="新增">
				<input type="hidden" id="action" name="action" value="add">
				<input type="hidden" name="uid" value="<%=smppuser2.getId()%>">
			</th>
			</form>
		</tr>
		
      </table>
      
      </div>
      </div>
      </div>
</div>

<%@include file="../foot.jsp" %>

</body>
</html>