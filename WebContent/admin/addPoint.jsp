<%@page import="com.scsb.bean.Admin"%>
<%@page import="com.scsb.dao.AdminDAO"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="com.scsb.dao.SmppuserDAO"%> 
<%@page import="com.scsb.bean.PointRecord"%>
<%@page import="com.scsb.dao.PointRecordDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  
  <% 
		
  		Smppuser smppuser = (Smppuser)session.getAttribute("smppuser");
  		Admin admin = (Admin)session.getAttribute("admin");
  		out.println("id:"+admin.getId());
		out.println("username:"+admin.getAccount());
		out.println("name"+admin.getName());
		out.println("<a href='"+request.getContextPath()+"/logout.do?type=admin'>登出</a>");

	%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>儲值系統</title>
</head>
<script type="text/javascript">


$(function(){
	$(".process").hide();

		$("#addPoint").validate({
			rules:{
				status:{
					required:true
				}
			},
			highlight: function( element, errorClass, validClass ) {
				//console.log(element.name+" "+element.type+" "+element.value);
				if ( element.type === "radio" ) {
					this.findByName(element.name).addClass(errorClass).removeClass(validClass);
				} else if( element.type === "select-one" || element.name ==="postcode") {
				} else {
					$(element).addClass(errorClass).removeClass(validClass);
				}
			},
			unhighlight: function( element, errorClass, validClass ) {

				if ( element.type === "radio" ) {
					this.findByName(element.name).removeClass(errorClass).addClass(validClass);
				}else if( element.type === "select-one" || element.name ==="postcode") {
				} else {
					$(element).removeClass(errorClass).addClass(validClass);
				}
			},   
	        submitHandler: function(form){
	           // if(confirm('Are you sure?'))form.submit();
	        	$(".process").fadeIn(1000);
	            form.submit();

	        }

		});
	});

</script>

<body>
	
	<form id="addPoint" action="addPoint.do" method="post">
	<input type="hidden" name="action" value="add"/>
		<div id="content">
			<table>												
				<tr>
					<td>儲值點數：</td>
					<td><input type="text" id="add_amount" name="add_amount"></td>
				</tr>
				<tr>
					<td>儲值金額：</td>
					<td><input type="text" id="add_price" name="add_price"></td>
				</tr>
				
			</table>
		</div>
		<input type=submit value=" 儲值 " name="submit"> 
		<input type=reset value=" 重填 " name="reset"> 
	</form>
    
</body>
</html>