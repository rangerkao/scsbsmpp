<%@page import="com.scsb.dao.OrderDAO" %>
<%@page import="com.scsb.bean.Order"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="com.scsb.dao.samplesms"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Smppuser smppuser2 = (Smppuser)session.getAttribute("smppuser"); 
	samplesms samples = new samplesms();
	String action = request.getParameter("action");

	if( action != null && action.length() > 0 )
	{
		out.println("Step A");
		
		if(action.equals("edit")  )
		{
			out.println("Step B");
			if(( request.getParameter("uid") != null ) && ( request.getParameter("sid") != null ))
			{		
				out.println("Step C");
				samples.setsample(request.getParameter("sid"), request.getParameter("sample"));	
			}
		}
		else if(action.equals("add") )
		{
			out.println("Step D");
			if(( request.getParameter("uid") != null ) && ( request.getParameter("sample") != null ))
			{		
				out.println("Step E");
				samples.addsample(request.getParameter("uid"), request.getParameter("sample"));				
			}
		}
		else if(action.equals("delete")  )
		{
			out.println("Step F");
			if(( request.getParameter("uid") != null ) && ( request.getParameter("sid") != null ))
			{		
				out.println("Step G");
				samples.deletesample(request.getParameter("sid"), request.getParameter("sid"));	
			}
		}
	}

	ArrayList<String[]> list = samples.getsamples(smppuser2.getId());

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>罐頭簡訊管理</title>
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
//<!-- Begin 
function textCounter(field, countfield, maxlimit) { 
	if (field.value.length > maxlimit) {
	field.value = field.value.substring(0,maxlimit);
	}else {
	countfield.value = maxlimit - field.value.length;
	}
	};
	// End -->

</script>

<style type="text/css">


</style>
<body>

<div id="main">
<%@include file="../head.jsp" %>
<div id="site_content">
      <div id="content" >
      <div  style="height:600px;overflow:auto; width:800px;">     
      <table border="5" cellpadding="8" frame="void" rules="none" width="100%" >
		<tr>
			<th>編號</th><th>罐頭樣式</th><th>&nbsp;</th>
		</tr>		
		<%
		int sample_num=1;
		for( String[] samplesms : list)
		{
		%>
			<form action="samplesms.jsp" method="post"> 
			<tr>
			<td><%=sample_num %></td>
			<td width=100>
			<textarea id="sample_<%=samplesms[0] %>" name="sample" cols="50" rows="7" style="resize : none;" maxlength="600" onKeyDown="textCounter(this.form.sample_<%=samplesms[0] %>,this.form.remLen_<%=samplesms[0] %>,300);" onKeyUp="textCounter(this.form.sample_<%=samplesms[0] %>,this.form.remLen_<%=samplesms[0] %>,300);"><%=samplesms[1] %></textarea>
			<pre>共可輸入300字，還剩<input readonly type=text id="remLen_<%=samplesms[0] %>" name="remLen" size=3 maxlength=3 value="<%=300 - samplesms[1].length() %>">字。</pre>
			
			</td>
			<td><input type="hidden" name="uid" value="<%=smppuser2.getId()%>">
			    <input type="hidden" name="sid" value="<%=samplesms[0]%>">
			    <input type="hidden" id="action_<%=samplesms[0] %>" name="action" value="">
			    <input type="submit" value="修改" onclick='document.getElementById("action_<%=samplesms[0] %>").value="edit";'>
			    <input type="submit" value="刪除" onclick='document.getElementById("action_<%=samplesms[0] %>").value="delete";'>
			</td>
			</tr>
			</form>
		<%
			sample_num++;
		}
		%>
		<tr>
			<form action="samplesms.jsp" method="post"> 
			<th></th><th>
			<textarea id="sample_new" name="sample" cols="50" rows="7" style="resize : none;" maxlength="600" onKeyDown="textCounter(this.form.sample_new,this.form.remLen,300);" onKeyUp="textCounter(this.form.sample_new,this.form.remLen,300);"></textarea>
			<pre>共可輸入300字，還剩<input readonly type=text id="remLen" name="remLen" size=3 maxlength=3 value="300">字。</pre>
			</th>
			<th>
				<input type="submit" value="新增">
				<input type="hidden" name="action" value="add">
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