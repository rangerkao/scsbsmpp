<%@page import="com.scsb.bean.Smppuser"%>
<%@page import="com.scsb.dao.SmppuserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	String id = request.getParameter("id");
    	SmppuserDAO smppuserDAO = new SmppuserDAO();
    	Smppuser smppuser = smppuserDAO.getSmppuser(id);
    %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改會員密碼</title>
</head>
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
<script type="text/javascript">


$(function(){

	$(".process").hide();

	$(".loading").hide();
	
	$("#imgcode").click(function(){
		var str = "../textBufferedImage.do?timestampt="+new Date().getTime();  
		$("#imgcode").attr("src",str);
	});
	
	$("#editForm").validate({
		rules:{
			password:{
				minlength:6,
				maxlength:32,
				alphanumeric:true
			},
			confirm_password:{
				equalTo:$("#password")
			},
			validcode:{
				checkValidCode:true
			},
			validcode:{
				checkValidCode:"驗證碼錯誤"
			}
		},
		messages:{
			password:{
				minlength:$.format("密碼長度至少為{0}"),		 
	            maxlength: $.format("密碼長度不可超過{0}")
			},
			confirm_password:{
				equalTo:"與設定密碼不一樣"
			}
		},
		errorPlacement: function (error, element) {
		     //console.log(element.attr('name'));
		     if (element.is(':radio') || element.is(':checkbox')) {
		         var eid = element.attr('name');
		         $('input[name=' + eid + ']:last').next().after(error);
		     } else if(element.attr('name')==="validcode"){
		    	 $('img[name=imgcode]').next().after(error);
		     } else {
		         error.insertAfter(element);
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
		success: function(label) {  
			label.addClass("valid").text("Ok!");      
		},          
        submitHandler: function(form){
           // if(confirm('Are you sure?'))form.submit(); //PROBLEM var has no such method
        	$(".process").fadeIn(1000);
            form.submit();

        }

	
		
	});
});

$.validator.addMethod("checkValidCode", function (value, element) {
	$('.loading').show();

    var ok = this.optional(element) || (/^([a-zA-Z0-9_]+)$/.test(value));
    if (ok) 
        $.ajax({
            url: '../checkValidCode.do', //你的验证页面，存在输出1，不存在输出0，不要输出其他的内容
            data: 'validcode=' + encodeURIComponent(value),
            async: false, /////////關鍵，設為同步
            type: 'post',
            dataType: 'json',
            success: function (data) {
            	$('.loading').hide();
                data = parseInt(data);
                ok = data == 0 ? false : true;
            },
            error: function (xhr) {
                alert('ajax有問題！\n' + xhr.responseText);
                ok = false;
            }
        });

    return ok;

}, "驗證碼錯誤");

</script>

<style type="text/css">


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
<div class="process">更改中</div>
<h1>會員資料</h1>
<form id="editForm" action="../editPassword.do" method="post">
	<input type="hidden" name="type" value="smppuser"/>
	<input type="hidden" name="action" value="editPassword"/>
	<input type="hidden" name="id" id="id" value="<%=smppuser.getId() %>">
	<h3>帳號資料</h3>
	<p>
		<label for="username">帳號</label>
		<span><%=smppuser.getUsername()%></span>
	</p>
	<p>
		<label for="password">密碼</label>
		<input type="password" id="password" name="password" value="" />
	</p>
		
	<p>
		<label for="confirm_password">確認密碼</label>
		<input type="password" id="confirm_password" name="confirm_password" value=""/>
	</p>		
	
	<p>
		<label for="company">公司名稱</label>
		<span><%=smppuser.getCompany()%></span>
	<p>
	
	<p>
		<label for="validcode">驗證碼</label>	
		<input type="text" id="validcode" name="validcode" size="4"/>
		<img id="imgcode" name="imgcode" height="30" width="90" src="../textBufferedImage.do?timestampt="+<%=new java.util.Date().getTime()%> title="點擊重刷" />
		<img class="loading" src="../images/loading_icon.gif">
	<p>
	
	
	<input class="submit" type="submit" name="send" value="修改"/>	
</form>
	 </div>
     </div>
   </div>
  </div>
 </div>
</div>

</body>
</html>