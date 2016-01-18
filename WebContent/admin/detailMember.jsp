<%@page import="com.scsb.bean.PointRecord"%>
<%@page import="com.scsb.dao.SmppuserDAO"%>
<%@page import="com.scsb.dao.PointRecordDAO"%>
<%@page import="com.scsb.bean.Admin"%>
<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<%
    	String id = request.getParameter("id");
		PointRecordDAO pointrecordDAO = new PointRecordDAO();
    	SmppuserDAO smppuserDAO = new SmppuserDAO();
    	Smppuser s = smppuserDAO.getSmppuser(id);
    	ArrayList<Smppuser> list = smppuserDAO.getSmppusers();
    %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員管理詳細</title>
<script type="text/javascript" src="../js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-validate.js"></script>
<script type="text/javascript" src="../js/jquery-validate-additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery-maskedinput.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.datepicker-zh-TW.min.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.browserplus.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.flash.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.gears.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.html5.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.html4.js"></script>
<script type="text/javascript" src="../js/plupload/jquery-plupload.full.js"></script>
<link rel="stylesheet" href="../css/jquery-ui-1.10.2.custom.css">
<link rel="stylesheet" type="text/css" href="/scsbsmpp/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/scsbsmpp/css/head_admin.css" />
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
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
<style type="text/css">

#frame{
	margin:-430px 0 0 435px;
}
#bg{
   /*background-color:rgba(25,7,45,0.5);*/
   
 }

</style>
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
          <div id="bg" style="overflow:auto;">
	<form id="addPoint" action="addPoint.do" method="post">
	<input type="hidden" name="action" value="add"/>
	<input type="hidden" name="id" value="<%=s.getId()%>" />
	<input type="hidden" id="smppuser_com" name="smppuser_com" value="<%=s.getCompany()%>" />
	<table border="5" cellpadding="8" frame="void" rules="none" style="color:#000000;">
		<tr><th>會員編號：</th><td><%=s.getId() %></td></tr>
		<tr><th>會員姓名：</th><td><%=s.getName()%></td></tr>	
		<tr><th>會員性別：</th>
		<td>
		<%
			int i = Integer.parseInt(s.getGender());
			if(i==0){
				out.println("男");
			}else if(i==1){
				out.println("女");
			}
		%>
		</td>
		</tr>
		<tr><th>公司名稱</th><td><%=s.getCompany() %></td></tr>
		<tr><th>會員電話：</th><td><%=s.getPhone() %></td></tr>	
		<tr><th>會員傳真：</th><td><%=s.getFax() %></td></tr>	
		<tr><th>會員E-mail：</th><td><%=s.getEmail() %></td></tr>	
		<tr><th>會員郵遞區號：</th><td><%=s.getPostcode() %></td></tr>	
		<tr><th>會員地址：</th><td><%=s.getAddress()%></td></tr>	
		<tr><th>會員登入IP：</th><td><%=s.getLogin_ip() %></td></tr>	
		<tr><th>會員登入時間：</th><td><%=s.getLogin_time() %></td></tr>	
		<tr><th>會員登出時間：</th><td><%=s.getEdit_time() %></td></tr>
		<tr><th>目前點數：</th><td><%=s.getPoint() %></td></tr>	
		<tr>
			<td>儲值點數：</td>
			<td><input type="text" id="add_amount" name="add_amount"></td>
		</tr>
		<tr>
			<td>儲值金額：</td>
			<td><input type="text" id="add_price" name="add_price"></td>
		</tr>
		<tr>
			<td>交易說明：</td>
			<td><input type="text" id="add_remark" name="add_remark"></td>
		</tr>
		<tr>
		<td><input type=submit value=" 儲值 " name="submit"></td>
		<td><input type=reset value=" 重填 " name="reset"> </td>
		</tr>										
	</table>
	</form>
	<input name="Submit" type="button" id="Submit" onClick="javascript:history.back(1)" value="回一上頁" />
	 </div>
     </div>
   </div>
  </div>
 </div>
</div>

</body>
</html>